package dasturlash.uz.service;

import dasturlash.uz.dto.CategoryDTO;
import dasturlash.uz.dto.SectionDTO;
import dasturlash.uz.entity.ArticleCategoryEntity;
import dasturlash.uz.entity.ArticleSectionEntity;
import dasturlash.uz.repository.ArticleCategoryRepository;
import dasturlash.uz.repository.ArticleSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ArticleCategoryService {
    @Autowired
    private ArticleCategoryRepository articleCategoryRepository;

    public void create(UUID articleId, List<CategoryDTO> categoryList) {
        for (CategoryDTO categoryDTO : categoryList) {
            ArticleCategoryEntity entity = new ArticleCategoryEntity();
            entity.setArticleId(articleId);
            entity.setCategoryId(categoryDTO.getId());
            articleCategoryRepository.save(entity);
        }
    }

    public void merge(UUID articleId, List<CategoryDTO> newList) {
        List<CategoryDTO> oldList = articleCategoryRepository.findSectionsByArticleId(articleId);
        newList.stream().filter(n -> !oldList.contains(n)).forEach(sd -> create(articleId, sd));
        oldList.stream().filter(old -> !newList.contains(old)).forEach(sd -> articleCategoryRepository.deleteByArticleIdAndCategoryId(articleId, sd.getId()));
    }

    public void create(UUID articleId, CategoryDTO categoryDTO) {
        ArticleCategoryEntity entity = new ArticleCategoryEntity();
        entity.setArticleId(articleId);
        entity.setCategoryId(categoryDTO.getId());
        articleCategoryRepository.save(entity);
    }
}
