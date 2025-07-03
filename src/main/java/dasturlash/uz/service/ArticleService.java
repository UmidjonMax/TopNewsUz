package dasturlash.uz.service;

import dasturlash.uz.dto.FilterResultDTO;
import dasturlash.uz.dto.article.ArticleCreateDTO;
import dasturlash.uz.dto.article.ArticleDTO;
import dasturlash.uz.dto.article.ArticleFilterDTO;
import dasturlash.uz.entity.ArticleEntity;
import dasturlash.uz.enums.AppLanguageEnum;
import dasturlash.uz.enums.ArticleStatusEnum;
import dasturlash.uz.exceptions.AppBadException;
import dasturlash.uz.mapper.ArticleShortInfo;
import dasturlash.uz.repository.ArticleCustomRepository;
import dasturlash.uz.repository.ArticleRepository;
import dasturlash.uz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleSectionService articleSectionService;
    @Autowired
    private ArticleCategoryService articleCategoryService;
    @Autowired
    private AttachService attachService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleCustomRepository articleCustomRepository;

    public ArticleDTO createArticle(ArticleCreateDTO createDTO) {

        ArticleEntity entity = new ArticleEntity();
        toEntity(createDTO, entity);

        // Set default values
        entity.setStatus(ArticleStatusEnum.NOT_PUBLISHED);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setVisible(true);
        entity.setViewCount(0);
        entity.setSharedCount(0L);
        entity.setModeratorId(SpringSecurityUtil.currentProfileId());
        ArticleEntity savedEntity = articleRepository.save(entity);
        // category -> merge
        articleCategoryService.merge(savedEntity.getId(), createDTO.getCategoryList());
        // section -> merge
        articleSectionService.merge(savedEntity.getId(), createDTO.getSectionList());
        // return
        return toDTO(entity);
    }

    public ArticleDTO update(String articleId, ArticleCreateDTO createDTO) {
        ArticleEntity entity = get(articleId);
        toEntity(createDTO, entity);
        // update
        articleRepository.save(entity);
        // category -> merge
        articleCategoryService.merge(entity.getId(), createDTO.getCategoryList());
        // section -> merge
        articleSectionService.merge(entity.getId(), createDTO.getSectionList());
        // return
        return toDTO(entity);
    }

    public String delete(String articleId) {
        int effectedRows = articleRepository.delete(articleId);
        if (effectedRows > 0) {
            return "Article deleted";
        } else {
            return "Something went wrong";
        }
//        ArticleEntity entity = get(articleId);
//        entity.setVisible(Boolean.FALSE);
//        articleRepository.save(entity);
//        return "Article deleted";
    }

    public String changeStatus(String articleId, ArticleStatusEnum status) {
        int effectedRows = articleRepository.changeStatus(articleId, status);
        if (effectedRows > 0) {
            return "Article status change";
        } else {
            return "Something went wrong";
        }
    }

    public List<ArticleDTO> getLastNArticlesBySection(Integer n, Integer sectionId) {
        List<ArticleShortInfo> entityList = articleRepository.findLastNArticlesBySectionId(n, sectionId);

        List<ArticleDTO> articleDTOList = new LinkedList<>();
        entityList.forEach(articleEntity -> articleDTOList.add(toDTO(articleEntity)));
        return articleDTOList;
    }

    public List<ArticleDTO> getLast12Articles(List<String> idList) {
        List<ArticleShortInfo> entityList = articleRepository.findLast12ArticlesExcept(idList);

        List<ArticleDTO> articleDTOList = new LinkedList<>();
        entityList.forEach(articleEntity -> articleDTOList.add(toDTO(articleEntity)));
        return articleDTOList;
    }

    public List<ArticleDTO> getLastNArticlesByCategory(Integer n, Integer categoryId) {
        List<ArticleShortInfo> entityList = articleRepository.findLastNArticlesByCategoryId(n, categoryId);

        List<ArticleDTO> articleDTOList = new LinkedList<>();
        entityList.forEach(articleEntity -> articleDTOList.add(toDTO(articleEntity)));
        return articleDTOList;
    }

    public List<ArticleDTO> getLastNArticlesByRegion(Integer n, Integer regionId) {
        List<ArticleShortInfo> entityList = articleRepository.findLastNArticlesByRegionId(n, regionId);

        List<ArticleDTO> articleDTOList = new LinkedList<>();
        entityList.forEach(articleEntity -> articleDTOList.add(toDTO(articleEntity)));
        return articleDTOList;
    }

    public List<ArticleDTO> findByTag(String tag, Integer limit) {
        List<ArticleShortInfo> entityList = articleRepository.findByTag(tag.toLowerCase(), limit);

        List<ArticleDTO> articleDTOList = new LinkedList<>();
        entityList.forEach(articleEntity -> articleDTOList.add(toDTO(articleEntity)));
        return articleDTOList;
    }

    public List<ArticleDTO> getLast4ArticlesBySection(Integer n, Integer sectionId, String id) {
        List<ArticleShortInfo> entityList = articleRepository.findLast4ArticlesBySectionId(n, id);

        List<ArticleDTO> articleDTOList = new LinkedList<>();
        entityList.forEach(articleEntity -> articleDTOList.add(toDTO(articleEntity)));
        return articleDTOList;
    }

    public List<ArticleDTO> getMostReadArticles(String id){
        List<ArticleShortInfo> entityList = articleRepository.findMostReadArticle(id);

        List<ArticleDTO> articleDTOList = new LinkedList<>();
        entityList.forEach(articleEntity -> articleDTOList.add(toDTO(articleEntity)));
        return articleDTOList;
    }
    //  13. Increase Article View Count by Article Id

    public Boolean increaseViewCount(String articleId) {
        // Qaysi IP orqali view qilinganini yozib qo'ysak yaxshi bo'lar edi, Shu IP dan shu maqolani o'qishdi deb.
        articleRepository.increaseViewCount(articleId);
        return Boolean.TRUE;
    }
    //  14. Increase Article Shared Count by Article Id

    public Integer increaseSharedCount(String articleId) {
        int sharedCount = articleRepository.incrementSharedCountAndGet(articleId);
        return sharedCount;
    }

    public Page<ArticleDTO> filter(ArticleFilterDTO filter, int page, int size) { // 1, 100
        FilterResultDTO<Object[]> filterResult = articleCustomRepository.filter(filter, page, size);
        List<ArticleDTO> articleList = new LinkedList<>();
        for (Object[] obj : filterResult.getContent()) {
            ArticleDTO article = new ArticleDTO();
            // a.id, a.title, a.description, a.publishedDate,a.imageId
            article.setId((String) obj[0]);
            article.setTitle((String) obj[1]);
            article.setDescription((String) obj[2]);
            article.setPublishedDate((LocalDateTime) obj[3]);
            if (obj[4] != null) {
                article.setImage(attachService.openDTO((String) obj[4]));
            }
            articleList.add(article);
        }
        return new PageImpl<>(articleList, PageRequest.of(page, size), filterResult.getTotal());
    }


    public ArticleDTO getById(String id, AppLanguageEnum lang) {
        // get
        ArticleEntity entity = get(id);

        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setSharedCount(entity.getSharedCount());
        dto.setReadTime(entity.getReadTime());
        dto.setViewCount(entity.getViewCount());
        dto.setPublishedDate(entity.getPublishedDate());
        dto.setLikeCount(entity.getLikeCount());
        // set region
        dto.setRegion(regionService.getByIdAndLang(entity.getRegionId(), lang));
        // set section
        // 1 -N
        dto.setSectionList(sectionService.getSectionListByArticleIdAndLang(entity.getId(), lang));
        // set category TODO
        dto.setCategoryList(categoryService.getCategoryListByArticleIdAndLang(entity.getId(), lang));
        // tag
        return dto;
    }

    private ArticleDTO toDTO(ArticleEntity entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setSharedCount(entity.getSharedCount());
        dto.setReadTime(entity.getReadTime());
        dto.setViewCount(entity.getViewCount());
        dto.setStatus(entity.getStatus());
        dto.setImageId(entity.getImageId());
        dto.setRegionId(entity.getRegionId());
        dto.setPublishedDate(entity.getPublishedDate());
        return dto;
    }


    private void toEntity(ArticleCreateDTO dto, ArticleEntity entity) {
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setImageId(dto.getImageId());
        entity.setRegionId(dto.getRegionId());
        entity.setReadTime(dto.getReadTime());
    }

    private ArticleDTO toDTO(ArticleShortInfo mapper) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(mapper.getId());
        dto.setTitle(mapper.getTitle());
        dto.setDescription(mapper.getDescription());
        dto.setImage(attachService.openDTO(mapper.getId()));
        dto.setPublishedDate(mapper.getPublishedDate());
        return dto;
    }

    public ArticleEntity get(String id) {
        return articleRepository.findById(id).orElseThrow(() -> {
            throw new AppBadException("Article not found");
        });
    }
}
