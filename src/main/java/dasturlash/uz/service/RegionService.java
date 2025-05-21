package dasturlash.uz.service;


import dasturlash.uz.dto.RegionDTO;
import dasturlash.uz.entity.RegionEntity;
import dasturlash.uz.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public RegionDTO createRegion(RegionDTO regionDTO) {
        var entity = regionRepository.save(mapToEntity().apply(regionDTO));
        regionDTO.setId(entity.getId());
        return regionDTO;
    }

    public RegionDTO updateRegion(Integer id, RegionDTO dto) {
        RegionEntity entity = regionRepository.findById(id).orElseThrow(RuntimeException::new);
        regionRepository.updateRegion(id, dto.getKey(), dto.getOrderNumber(), dto.getNameUz(), dto.getNameRu(), dto.getNameEn());
        return toDTO().apply(entity);
    }

    public boolean deleteRegion(Integer id) {
        regionRepository.delete(id);
        return true;
    }

    public List<RegionDTO> getAllRegions() {
        Iterable<RegionEntity> regions = regionRepository.findAllByVisibleIsTrue();
        List<RegionDTO> dtos = new ArrayList<>();
        regions.forEach(region -> dtos.add(toDTO().apply(region)));
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
            regionEntity.setId(regionDTO.getId());
            regionEntity.setKey(regionDTO.getKey());
            regionEntity.setOrderNumber(regionDTO.getOrderNumber());
            regionEntity.setNameUz(regionDTO.getNameUz());
            regionEntity.setNameRu(regionDTO.getNameRu());
            regionEntity.setNameEn(regionDTO.getNameEn());
            return regionEntity;
        };
    }
}
