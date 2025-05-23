package dasturlash.uz.service;

import dasturlash.uz.dto.CategoryDTO;
import dasturlash.uz.dto.RegionDTO;
import dasturlash.uz.entity.CategoryEntity;
import dasturlash.uz.entity.RegionEntity;
import dasturlash.uz.enums.AppLanguageEnum;
import dasturlash.uz.exceptions.AppBadException;
import dasturlash.uz.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryDTO dto) {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findByKey(dto.getKey());
        if (categoryEntity.isPresent()) {
            throw new AppBadException("Category already exists");
        }
        var entity = categoryRepository.save(mapToEntity().apply(dto));
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public CategoryDTO update(Integer id, CategoryDTO dto) {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findByIdAndVisibleIsTrue(id);
        if (categoryEntity.isEmpty()) {
            throw new AppBadException("Category does not exist");
        }
        Optional<CategoryEntity> keyOpt = categoryRepository.findByKey(dto.getKey());
        if (keyOpt.isPresent() && !id.equals(keyOpt.get().getId())) {
            throw new AppBadException("Category Key already exists");
        }
        CategoryEntity entity = categoryEntity.get();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setKey(dto.getKey());
        categoryRepository.save(entity);
        dto.setId(id);
        return dto;
    }

    public Boolean delete(Integer id) {
        return categoryRepository.updateVisible(id) == 1;
    }

    public List<CategoryDTO> getAll() {
        Iterable<CategoryEntity> categories = categoryRepository.findAllByOrderSorted();
        List<CategoryDTO> dtos = new LinkedList<>();
        categories.forEach(category -> dtos.add(toDTO().apply(category)));
        return dtos;
    }

    public List<CategoryDTO> getAllByLang(AppLanguageEnum lang) {
        Iterable<CategoryEntity> categories = categoryRepository.findAllByOrderSorted();
        List<CategoryDTO> dtos = new LinkedList<>();
        categories.forEach(category -> dtos.add(toLangResponseDto(lang, category)));
        return dtos;
    }

    public Function<CategoryEntity, CategoryDTO> toDTO() {
        return categoryEntity -> {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(categoryEntity.getId());
            categoryDTO.setKey(categoryEntity.getKey());
            categoryDTO.setOrderNumber(categoryEntity.getOrderNumber());
            categoryDTO.setNameUz(categoryEntity.getNameUz());
            categoryDTO.setNameRu(categoryEntity.getNameRu());
            categoryDTO.setNameEn(categoryEntity.getNameEn());
            categoryDTO.setCreatedDate(categoryEntity.getCreatedDate());
            return categoryDTO;
        };
    }

    public Function<CategoryDTO, CategoryEntity> mapToEntity() {
        return categoryDTO -> {
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setId(categoryDTO.getId());
            categoryEntity.setKey(categoryDTO.getKey());
            categoryEntity.setOrderNumber(categoryDTO.getOrderNumber());
            categoryEntity.setNameUz(categoryDTO.getNameUz());
            categoryEntity.setNameRu(categoryDTO.getNameRu());
            categoryEntity.setNameEn(categoryDTO.getNameEn());
            return categoryEntity;
        };
    }

    private CategoryDTO toLangResponseDto(AppLanguageEnum lang, CategoryEntity entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setKey(entity.getKey());
        switch (lang) {
            case EN:
                dto.setNameEn(entity.getNameEn());
                break;
            case RU:
                dto.setNameRu(entity.getNameRu());
                break;
            case UZ:
                dto.setNameUz(entity.getNameUz());
                break;
        }
        return dto;
    }
}
