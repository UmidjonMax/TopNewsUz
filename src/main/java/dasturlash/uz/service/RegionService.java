package dasturlash.uz.service;


import dasturlash.uz.dto.RegionDTO;
import dasturlash.uz.entity.RegionEntity;
import dasturlash.uz.enums.AppLanguageEnum;
import dasturlash.uz.exceptions.AppBadException;
import dasturlash.uz.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public RegionDTO create(RegionDTO regionDTO) {
        Optional<RegionEntity> region = regionRepository.findAllByKey(regionDTO.getKey());
        if (region.isPresent()) {
            throw new AppBadException("Region already exists");
        }
        var entity = regionRepository.save(mapToEntity().apply(regionDTO));
        regionDTO.setId(entity.getId());
        regionDTO.setCreatedDate(entity.getCreatedDate());
        return regionDTO;
    }

    public RegionDTO update(Integer id, RegionDTO dto) {
        Optional<RegionEntity> region = regionRepository.findAllByIdAndVisibleIsTrue(id);
        if (region.isEmpty()){
            throw new AppBadException("Region not found");
        }
        Optional<RegionEntity> keyOptional = regionRepository.findAllByKey(dto.getKey());
        if (keyOptional.isPresent() && !id.equals(keyOptional.get().getId())) {
            throw new AppBadException("Region key already exists");
        }
        RegionEntity entity = region.get();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setKey(dto.getKey());
        regionRepository.save(entity);
        dto.setId(id);
        return dto;
    }

    public boolean delete(Integer id) {
        return regionRepository.updateVisible(id) == 1;
    }

    public List<RegionDTO> getAllRegions() {
        Iterable<RegionEntity> regions = regionRepository.findAllSorted();
        List<RegionDTO> dtos = new LinkedList<>();
        regions.forEach(region -> dtos.add(toDTO().apply(region)));
        return dtos;
    }

    public List<RegionDTO> getAllByLang(AppLanguageEnum lang){
        Iterable<RegionEntity> regions = regionRepository.findAllSorted();
        List<RegionDTO> dtos = new LinkedList<>();
        regions.forEach(region -> dtos.add(toLangResponseDto(lang, region)));
        return dtos;
    }

    public Function<RegionEntity, RegionDTO> toDTO() {
        return regionEntity -> {
            RegionDTO regionDTO = new RegionDTO();
            regionDTO.setId(regionEntity.getId());
            regionDTO.setKey(regionEntity.getKey());
            regionDTO.setOrderNumber(regionEntity.getOrderNumber());
            regionDTO.setNameUz(regionEntity.getNameUz());
            regionDTO.setNameRu(regionEntity.getNameRu());
            regionDTO.setNameEn(regionEntity.getNameEn());
            return regionDTO;
        };
    }

    public Function<RegionDTO, RegionEntity> mapToEntity() {
        return regionDTO ->{
            RegionEntity regionEntity = new RegionEntity();
            regionEntity.setOrderNumber(regionDTO.getOrderNumber());
            regionEntity.setNameUz(regionDTO.getNameUz());
            regionEntity.setNameRu(regionDTO.getNameRu());
            regionEntity.setNameEn(regionDTO.getNameEn());
            regionEntity.setKey(regionDTO.getKey());
            return regionEntity;
        };
    }

    private RegionDTO toLangResponseDto(AppLanguageEnum lang, RegionEntity entity) {
        RegionDTO dto = new RegionDTO();
        dto.setId(entity.getId());
        dto.setKey(entity.getKey());
        switch (lang) {
            case EN:
                dto.setName(entity.getNameEn());
                break;
            case RU:
                dto.setName(entity.getNameRu());
                break;
            case UZ:
                dto.setName(entity.getNameUz());
                break;
        }
        return dto;
    }
}
