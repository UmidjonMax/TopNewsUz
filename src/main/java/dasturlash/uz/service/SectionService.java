package dasturlash.uz.service;

import dasturlash.uz.dto.CategoryDTO;
import dasturlash.uz.dto.SectionDTO;
import dasturlash.uz.entity.CategoryEntity;
import dasturlash.uz.entity.SectionEntity;
import dasturlash.uz.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SectionService {
    @Autowired
    private SectionRepository sectionRepository;

    public SectionDTO create(SectionDTO dto) {
        SectionEntity section = new SectionEntity();
        section.setOrderNumber(dto.getOrderNumber());
        section.setNameUz(dto.getNameUz());
        section.setNameRu(dto.getNameRu());
        section.setNameEn(dto.getNameEn());
        section.setKey(dto.getKey());
        sectionRepository.save(section);
        dto.setId(section.getId());
        return dto;
    }

    public SectionDTO update(Integer id, SectionDTO dto) {
        int effectedRows = sectionRepository.update(id, dto.getKey(), dto.getNameUz(), dto.getNameRu(), dto.getNameEn());
        return dto;
    }

    public boolean delete(Integer id) {
        sectionRepository.deleteById(id);
        return true;
    }

    public List<SectionDTO> getAll() {
        Iterable<SectionEntity> sections = sectionRepository.findAll();
        List<SectionDTO> dtos = new ArrayList<>();
        sections.forEach(section -> dtos.add(toDTO(section)));
        return dtos;
    }

    public SectionDTO toDTO(SectionEntity entity) {
        SectionDTO dtos = new SectionDTO();
        dtos.setId(entity.getId());
        dtos.setOrderNumber(entity.getOrderNumber());
        dtos.setNameUz(entity.getNameUz());
        dtos.setNameRu(entity.getNameRu());
        dtos.setNameEn(entity.getNameEn());
        dtos.setKey(entity.getKey());
        dtos.setCreatedDate(entity.getCreatedDate());
        return dtos;
    }
}
