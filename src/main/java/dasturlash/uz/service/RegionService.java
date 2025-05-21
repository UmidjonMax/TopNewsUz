package dasturlash.uz.service;

import dasturlash.uz.dto.RegionDTO;
import dasturlash.uz.entity.RegionEntity;
import dasturlash.uz.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public RegionDTO createRegion(RegionDTO regionDTO) {
        RegionEntity region = new RegionEntity();
        region.setOrderNumber(regionDTO.getOrderNumber());
        region.setNameUz(regionDTO.getNameUz());
        region.setNameRu(regionDTO.getNameRu());
        region.setNameEn(regionDTO.getNameEn());
        region.setKey(regionDTO.getKey());
        regionRepository.save(region);
        regionDTO.setId(region.getId());
        return regionDTO;
    }

    public RegionDTO updateRegion(Integer id, RegionDTO dto) {
        int effectedRows = regionRepository.updateRegion(id, dto.getKey(), dto.getNameUz(), dto.getNameRu(), dto.getNameEn());
        return dto;
    }

    public boolean deleteRegion(Integer id) {
        regionRepository.deleteById(id);
        return true;
    }

    public List<RegionDTO> getAllRegions() {
        Iterable<RegionEntity> regions = regionRepository.findAll();
        List<RegionDTO> dtos = new ArrayList<>();
        regions.forEach(region -> dtos.add(toDTO(region)));
        return dtos;
    }

    public RegionDTO toDTO(RegionEntity regionEntity) {
        RegionDTO regionDTO = new RegionDTO();
        regionDTO.setId(regionEntity.getId());
        regionDTO.setOrderNumber(regionEntity.getOrderNumber());
        regionDTO.setNameUz(regionEntity.getNameUz());
        regionDTO.setNameRu(regionEntity.getNameRu());
        regionDTO.setNameEn(regionEntity.getNameEn());
        regionDTO.setKey(regionEntity.getKey());
        regionDTO.setCreatedDate(regionEntity.getCreatedDate());
        return regionDTO;
    }
}
