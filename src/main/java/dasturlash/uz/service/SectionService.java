package dasturlash.uz.service;

import dasturlash.uz.dto.SectionDTO;
import dasturlash.uz.entity.SectionEntity;
import dasturlash.uz.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class SectionService {
    @Autowired
    private SectionRepository sectionRepository;

    public SectionDTO create(SectionDTO dto) {
        var entity = sectionRepository.save(mapToEntity().apply(dto));
        dto.setId(entity.getId());
        return dto;
    }

    public SectionDTO update(Integer id, SectionDTO dto) {
        int effectedRows = sectionRepository.update(id, dto.getKey(), dto.getNameUz(), dto.getNameRu(), dto.getNameEn());
        return dto;
    }

    public boolean delete(Integer id) {
        sectionRepository.delete(id);
        return true;
    }

    public List<SectionDTO> getAll() {
        Iterable<SectionEntity> sections = sectionRepository.findAll();
        List<SectionDTO> dtos = new ArrayList<>();
        sections.forEach(section -> dtos.add(toDTO().apply(section)));
        return dtos;
    }

    public Function<SectionEntity, SectionDTO> toDTO() {
        return sectionEntity -> {
            SectionDTO sectionDTO = new SectionDTO();
            sectionDTO.setId(sectionEntity.getId());
            sectionDTO.setKey(sectionEntity.getKey());
            sectionDTO.setOrderNumber(sectionEntity.getOrderNumber());
            sectionDTO.setNameUz(sectionEntity.getNameUz());
            sectionDTO.setNameRu(sectionEntity.getNameRu());
            sectionDTO.setNameEn(sectionEntity.getNameEn());
            return sectionDTO;
        };
    }

    public Function<SectionDTO, SectionEntity> mapToEntity() {
        return sectionDTO ->{
            SectionEntity sectionEntity = new SectionEntity();
            sectionEntity.setId(sectionDTO.getId());
            sectionEntity.setKey(sectionDTO.getKey());
            sectionEntity.setOrderNumber(sectionDTO.getOrderNumber());
            sectionEntity.setNameUz(sectionDTO.getNameUz());
            sectionEntity.setNameRu(sectionDTO.getNameRu());
            sectionEntity.setNameEn(sectionDTO.getNameEn());
            return sectionEntity;
        };
    }
}
