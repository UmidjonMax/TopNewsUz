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

    public void merge(String articleId, List<CategoryDTO> dtoList) {
        List<Integer> newList = dtoList.stream().map(CategoryDTO::getId).toList();

        List<Integer> oldList = articleCategoryRepository.getCategoryIdListByArticleId(articleId);
        newList.stream().filter(n -> !oldList.contains(n)).forEach(pe -> create(articleId, pe)); // create
        oldList.stream().filter(old -> !newList.contains(old)).forEach(pe -> articleCategoryRepository.deleteByCategoryIdAndArticleId(articleId, pe));
    }

    public void create(String articleId, Integer categoryId) {
        ArticleCategoryEntity entity = new ArticleCategoryEntity();
        entity.setArticleId(articleId);
        entity.setCategoryId(categoryId);
        articleCategoryRepository.save(entity);
    }
}
