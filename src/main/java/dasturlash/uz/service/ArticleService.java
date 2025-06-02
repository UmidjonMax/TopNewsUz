package dasturlash.uz.service;

import dasturlash.uz.dto.ArticleDTO;
import dasturlash.uz.dto.ArticleShortInfoDTO;
import dasturlash.uz.entity.ArticleEntity;
import dasturlash.uz.enums.ArticleStatusEnum;
import dasturlash.uz.exceptions.AppBadException;
import dasturlash.uz.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleSectionService articlesectionService;
    @Autowired
    private ArticleCategoryService articleCategoryService;

    public ArticleShortInfoDTO create(ArticleDTO articleDTO) {
        Optional<ArticleEntity> optional = articleRepository.findByTitleAndVisibleIsTrue(articleDTO.getTitle());
        if (optional.isPresent()) {
            throw new AppBadException("Article already exists");
        }
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setTitle(articleDTO.getTitle());
        articleEntity.setDescription(articleDTO.getDescription());
        articleEntity.setContent(articleDTO.getContent());
        articleEntity.setRegionId(articleDTO.getRegionId());
        articleEntity.setProfileId(articleDTO.getProfileId());
        articleRepository.save(articleEntity);
        articleCategoryService.create(articleEntity.getId(), articleDTO.getCategoryList());
        articlesectionService.create(articleEntity.getId(), articleDTO.getSectionList());
        ArticleShortInfoDTO shortInfo = new ArticleShortInfoDTO();
        shortInfo.setId(articleEntity.getId());
        shortInfo.setTitle(articleEntity.getTitle());
        shortInfo.setDescription(articleEntity.getDescription());
        shortInfo.setPublishedDate(articleEntity.getPublishedDate());

        return shortInfo;
    }

    public ArticleShortInfoDTO update(UUID id, ArticleDTO articleDTO) {
        ArticleEntity entity = articleRepository.findByIdAndVisibleIsTrue(id).orElseThrow(() -> new AppBadException("Article not found"));
        Optional<ArticleEntity> articleOpt = articleRepository.findByTitleAndVisibleIsTrue(articleDTO.getTitle());

        if (articleOpt.isPresent() && !articleOpt.get().getId().equals(id)) {
            throw new AppBadException("Article already exists");
        }
        entity.setTitle(articleDTO.getTitle());
        entity.setDescription(articleDTO.getDescription());
        entity.setContent(articleDTO.getContent());
        entity.setRegionId(articleDTO.getRegionId());
        entity.setStatus(ArticleStatusEnum.NOT_PUBLISHED);
        articleRepository.save(entity);
        articleCategoryService.merge(articleDTO.getId(), articleDTO.getCategoryList());
        articlesectionService.merge(articleDTO.getId(), articleDTO.getSectionList());
        ArticleShortInfoDTO shortInfo = new ArticleShortInfoDTO();
        shortInfo.setId(articleDTO.getId());
        shortInfo.setTitle(articleDTO.getTitle());
        shortInfo.setDescription(articleDTO.getDescription());
        shortInfo.setPublishedDate(entity.getPublishedDate());
        return shortInfo;
    }

    public boolean delete(UUID id) {
        return articleRepository.updateVisible(id) == 1;
    }

    public String changeStatus(UUID id, ArticleStatusEnum status) {
        if(articleRepository.updateStatus(id, status) == 1) {
            return "Status changed to " + status;
        }
        return "Status not changed to " + status;
    }
}
