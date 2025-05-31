package dasturlash.uz.service;

import dasturlash.uz.dto.ArticleDTO;
import dasturlash.uz.entity.ArticleEntity;
import dasturlash.uz.exceptions.AppBadException;
import dasturlash.uz.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleSectionService articlesectionService;
    @Autowired
    private ArticleCategoryService articleCategoryService;

    public ArticleDTO create(ArticleDTO articleDTO) {
        Optional<ArticleEntity> optional = articleRepository.findByTitleAndVisibleIsTrue(articleDTO.getTitle());
        if (optional.isPresent()) {
            throw new AppBadException("Article already exists");
        }
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setTitle(articleDTO.getTitle());
        articleEntity.setDescription(articleDTO.getDescription());
        articleEntity.setContent(articleDTO.getContent());
        articleEntity.setRegionId(articleDTO.getRegionId());
        articleRepository.save(articleEntity);
        articleCategoryService.merge(articleEntity.getId(), articleDTO.getCategoryList());
        articlesectionService.merge(articleEntity.getId(), articleDTO.getSectionList());
        ArticleDTO response = toDTO(articleEntity);
        response.setSectionList(articleDTO.getSectionList());
        response.setCategoryList(articleDTO.getCategoryList());
        return response;
    }

    public ArticleDTO toDTO(ArticleEntity articleEntity) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(articleEntity.getId());
        articleDTO.setTitle(articleEntity.getTitle());
        articleDTO.setDescription(articleEntity.getDescription());
        articleDTO.setContent(articleEntity.getContent());
        articleDTO.setRegionId(articleEntity.getRegionId());
        return articleDTO;
    }
}
