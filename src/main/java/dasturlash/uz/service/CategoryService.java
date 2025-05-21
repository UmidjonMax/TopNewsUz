package dasturlash.uz.service;

import dasturlash.uz.dto.CategoryDTO;
import dasturlash.uz.dto.RegionDTO;
import dasturlash.uz.entity.CategoryEntity;
import dasturlash.uz.entity.RegionEntity;
import dasturlash.uz.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryDTO dto) {
        var entity = categoryRepository.save(mapToEntity().apply(dto));
        dto.setId(entity.getId());
        return dto;
    }

    public CategoryDTO update(Integer id, CategoryDTO dto) {
        int effectedRows = categoryRepository.updateCategory(id, dto.getKey(), dto.getNameUz(), dto.getNameRu(), dto.getNameEn());
        return dto;
    }

    public boolean delete(Integer id) {
        categoryRepository.delete(id);
        return true;
    }

    public List<CategoryDTO> getAll() {
        Iterable<CategoryEntity> categories = categoryRepository.findAll();
        List<CategoryDTO> dtos = new ArrayList<>();
        categories.forEach(category -> dtos.add(toDTO().apply(category)));
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
            return categoryDTO;
        };
    }

    public Function<CategoryDTO, CategoryEntity> mapToEntity() {
        return categoryDTO ->{
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
}
