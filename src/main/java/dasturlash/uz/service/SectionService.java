package dasturlash.uz.service;

import dasturlash.uz.dto.SectionDTO;
import dasturlash.uz.entity.SectionEntity;
import dasturlash.uz.enums.AppLanguageEnum;
import dasturlash.uz.exceptions.AppBadException;
import dasturlash.uz.mapper.SectionMapper;
import dasturlash.uz.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class SectionService {
    @Autowired
    private SectionRepository sectionRepository;

    public SectionDTO create(SectionDTO dto) {
        Optional<SectionEntity> section = sectionRepository.findByKey(dto.getKey());
        if (section.isPresent()) {
            throw new AppBadException("Section already exists");
        }
        var entity = sectionRepository.save(mapToEntity().apply(dto));
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public SectionDTO update(Integer id, SectionDTO dto) {
        Optional<SectionEntity> section = sectionRepository.findByIdAndVisibleIsTrue(id);
        if (section.isEmpty()) {
            throw new AppBadException("Section does not exist");
        }
        Optional<SectionEntity> keyOpt = sectionRepository.findByKey(dto.getKey());
        if (keyOpt.isPresent() && !id.equals(keyOpt.get().getId())) {
            throw new AppBadException("Section Key already exists");
        }
        SectionEntity entity = section.get();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setKey(dto.getKey());
        sectionRepository.save(entity);
        dto.setId(id);
        return dto;
    }

    public Boolean delete(Integer id) {
        return sectionRepository.updateVisible(id) == 1;
    }

    public List<SectionDTO> getAll() {
        Iterable<SectionEntity> sections = sectionRepository.findAllByOrderSorted();
        List<SectionDTO> dtos = new LinkedList<>();
        sections.forEach(section -> dtos.add(toDTO().apply(section)));
        return dtos;
    }

    public List<SectionDTO> getAllByLang(AppLanguageEnum lang) {
        Iterable<SectionMapper> iterable = sectionRepository.getByLang(lang.name());
        List<SectionDTO> dtoList = new LinkedList<>();
        iterable.forEach(mapper -> {
            SectionDTO dto = new SectionDTO();
            dto.setId(mapper.getId());
            dto.setName(mapper.getName());
            dto.setKey(mapper.getKey());
            dtoList.add(dto);
        });
        return dtoList;
    }

    public List<SectionDTO> getSectionListByArticleIdAndLang(String articleId, AppLanguageEnum lang) {
        List<SectionMapper> iterable = sectionRepository.getSectionListByArticleIdAndLang(articleId, lang.name());
        List<SectionDTO> dtoList = new LinkedList<>();
        iterable.forEach(mapper -> {
            SectionDTO dto = new SectionDTO();
            dto.setId(mapper.getId());
            dto.setName(mapper.getName());
            dto.setKey(mapper.getKey());
            dtoList.add(dto);
        });
        return dtoList;
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
            sectionEntity.setOrderNumber(sectionDTO.getOrderNumber());
            sectionEntity.setNameUz(sectionDTO.getNameUz());
            sectionEntity.setNameRu(sectionDTO.getNameRu());
            sectionEntity.setNameEn(sectionDTO.getNameEn());
            sectionEntity.setKey(sectionDTO.getKey());
            return sectionEntity;
        };
    }
}
