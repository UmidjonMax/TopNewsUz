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

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryDTO dto) {
        CategoryEntity category = new CategoryEntity();
        category.setOrderNumber(dto.getOrderNumber());
        category.setNameUz(dto.getNameUz());
        category.setNameRu(dto.getNameRu());
        category.setNameEn(dto.getNameEn());
        category.setKey(dto.getKey());
        categoryRepository.save(category);
        dto.setId(category.getId());
        return dto;
    }

    public CategoryDTO update(Integer id, CategoryDTO dto) {
        int effectedRows = categoryRepository.updateCategory(id, dto.getKey(), dto.getNameUz(), dto.getNameRu(), dto.getNameEn());
        return dto;
    }

    public boolean delete(Integer id) {
        categoryRepository.deleteById(id);
        return true;
    }

    public List<CategoryDTO> getAll() {
        Iterable<CategoryEntity> categories = categoryRepository.findAll();
        List<CategoryDTO> dtos = new ArrayList<>();
        categories.forEach(category -> dtos.add(toDTO(category)));
        return dtos;
    }

    public CategoryDTO toDTO(CategoryEntity entity) {
        CategoryDTO dtos = new CategoryDTO();
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
