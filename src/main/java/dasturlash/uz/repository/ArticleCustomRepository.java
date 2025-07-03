package dasturlash.uz.repository;

import dasturlash.uz.dto.FilterResultDTO;
import dasturlash.uz.dto.article.ArticleFilterDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ArticleCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public FilterResultDTO<Object[]> filter(ArticleFilterDTO filter, int size, int page) {

        StringBuilder selectQueryBuilder = new StringBuilder("SELECT a FROM ArticleEntity a ");
        // 1 - N
        StringBuilder countQueryBuilder = new StringBuilder("SELECT count(a) FROM ArticleEntity a ");

        StringBuilder builder = new StringBuilder(" where a.visible = true and a.status = 'PUBLISHED' ");

        Map<String, Object> params = new HashMap<>();

        if (filter.getTitle() != null) {
            builder.append(" and  lower(a.title) like :title ");
            params.put("title", "%" + filter.getTitle().toLowerCase() + "%");
        }
        if (filter.getRegionId() != null) {
            builder.append(" and  a.regionId =:regionId");
            params.put("regionId", filter.getRegionId());
        }
        if (filter.getSectionId() != null) {
            selectQueryBuilder.append(" inner join ArticleSectionEntity ase on ase.articleId = a.id  ");
            countQueryBuilder.append(" inner join ArticleSectionEntity ase on ase.articleId = a.id  ");

            builder.append(" and  ase.sectionId =:sectionId");
            params.put("sectionId", filter.getSectionId());
        }
        // category TODO
        if (filter.getCategoryId() != null) {
            selectQueryBuilder.append(" inner join ArticleCategoryEntity ace on ace.articleId = a.id  ");
            countQueryBuilder.append(" inner join ArticleCategoryEntity ace on ace.articleId = a.id  ");

            builder.append(" and  ace.categoryId =:categoryId");
            params.put("categoryId", filter.getCategoryId());
        }
        if (filter.getCreatedDateTo() != null && filter.getCreatedDateFrom() != null) {
            builder.append(" and a.publishDate between :createdDateFrom and :createdDateTo ");
            params.put("createdDateTo", filter.getCreatedDateTo());
            params.put("createdDateFrom", filter.getCreatedDateFrom());
        } else if (filter.getCreatedDateFrom() != null) {
            builder.append(" and a.publishDate > :createdDateFrom ");
            params.put("createdDateFrom", filter.getCreatedDateFrom());
        } else if (filter.getCreatedDateTo() != null) {
            builder.append(" and a.publishDate < :createdDateTo ");
            params.put("createdDateTo", filter.getCreatedDateTo());
        }

        selectQueryBuilder.append(builder);
        countQueryBuilder.append(builder);

        // select query
        Query selectQuery = entityManager.createQuery(selectQueryBuilder.toString());
        selectQuery.setFirstResult((page) * size); // 50
        selectQuery.setMaxResults(size); // 30
        params.forEach(selectQuery::setParameter);

        List<Object[]> articleList = selectQuery.getResultList();

        // totalCount query
        Query countQuery = entityManager.createQuery(countQueryBuilder.toString());
        params.forEach(countQuery::setParameter);

        Long totalElements = (Long) countQuery.getSingleResult();

        return new FilterResultDTO<Object[]>(articleList, totalElements);
    }

    public FilterResultDTO<Object[]> filterModerator(ArticleFilterDTO filter, int size, int page) {

        StringBuilder selectQueryBuilder = new StringBuilder("SELECT a FROM ArticleEntity a ");
        // 1 - N
        StringBuilder countQueryBuilder = new StringBuilder("SELECT count(a) FROM ArticleEntity a ");

        StringBuilder builder = new StringBuilder(" where a.visible = true");

        Map<String, Object> params = new HashMap<>();

        if (filter.getTitle() != null) {
            builder.append(" and  lower(a.title) like :title ");
            params.put("title", "%" + filter.getTitle().toLowerCase() + "%");
        }
        if (filter.getRegionId() != null) {
            builder.append(" and  a.regionId =:regionId");
            params.put("regionId", filter.getRegionId());
        }
        if (filter.getSectionId() != null) {
            selectQueryBuilder.append(" inner join ArticleSectionEntity ase on ase.articleId = a.id  ");
            countQueryBuilder.append(" inner join ArticleSectionEntity ase on ase.articleId = a.id  ");

            builder.append(" and  ase.sectionId =:sectionId");
            params.put("sectionId", filter.getSectionId());
        }
        // category TODO
        if (filter.getCategoryId() != null) {
            selectQueryBuilder.append(" inner join ArticleCategoryEntity ace on ace.articleId = a.id  ");
            countQueryBuilder.append(" inner join ArticleCategoryEntity ace on ace.articleId = a.id  ");

            builder.append(" and  ace.categoryId =:categoryId");
            params.put("categoryId", filter.getCategoryId());
        }
        if (filter.getPublishedDateFrom() != null && filter.getPublishedDateTo() != null) {
            builder.append(" and a.publishDate between :publishedDateFrom and :publishedDateTo ");
            params.put("publishedDateTo", filter.getPublishedDateTo());
            params.put("publishedDateFrom", filter.getPublishedDateFrom());
        } else if (filter.getPublishedDateFrom() != null) {
            builder.append(" and a.publishDate > :publishedDateFrom ");
            params.put("publishedDateFrom", filter.getPublishedDateFrom());
        } else if (filter.getPublishedDateTo() != null) {
            builder.append(" and a.publishDate < :publishedDateTo ");
            params.put("publishedDateTo", filter.getPublishedDateTo());
        }

        if (filter.getCreatedDateTo() != null && filter.getCreatedDateFrom() != null) {
            builder.append(" and a.createdDate between :createdDateFrom and :createdDateTo ");
            params.put("createdDateTo", filter.getCreatedDateTo());
            params.put("createdDateFrom", filter.getCreatedDateFrom());
        } else if (filter.getCreatedDateFrom() != null) {
            builder.append(" and a.createdDate > :createdDateFrom ");
            params.put("createdDateFrom", filter.getCreatedDateFrom());
        } else if (filter.getCreatedDateTo() != null) {
            builder.append(" and a.createdDate < :createdDateTo ");
            params.put("createdDateTo", filter.getCreatedDateTo());
        }

        if (filter.getStatus() != null) {
            builder.append(" and  a.status = :status");
            params.put("status", filter.getStatus());
        }

        selectQueryBuilder.append(builder);
        countQueryBuilder.append(builder);

        // select query
        Query selectQuery = entityManager.createQuery(selectQueryBuilder.toString());
        selectQuery.setFirstResult((page) * size); // 50
        selectQuery.setMaxResults(size); // 30
        params.forEach(selectQuery::setParameter);

        List<Object[]> articleList = selectQuery.getResultList();

        // totalCount query
        Query countQuery = entityManager.createQuery(countQueryBuilder.toString());
        params.forEach(countQuery::setParameter);

        Long totalElements = (Long) countQuery.getSingleResult();

        return new FilterResultDTO<Object[]>(articleList, totalElements);
    }

    public FilterResultDTO<Object[]> filterPublisher(ArticleFilterDTO filter, int size, int page) {

        StringBuilder selectQueryBuilder = new StringBuilder("SELECT a FROM ArticleEntity a ");
        // 1 - N
        StringBuilder countQueryBuilder = new StringBuilder("SELECT count(a) FROM ArticleEntity a ");

        StringBuilder builder = new StringBuilder(" where a.visible = true");

        Map<String, Object> params = new HashMap<>();

        if (filter.getTitle() != null) {
            builder.append(" and  lower(a.title) like :title ");
            params.put("title", "%" + filter.getTitle().toLowerCase() + "%");
        }
        if (filter.getRegionId() != null) {
            builder.append(" and  a.regionId =:regionId");
            params.put("regionId", filter.getRegionId());
        }
        if (filter.getSectionId() != null) {
            selectQueryBuilder.append(" inner join ArticleSectionEntity ase on ase.articleId = a.id  ");
            countQueryBuilder.append(" inner join ArticleSectionEntity ase on ase.articleId = a.id  ");

            builder.append(" and  ase.sectionId =:sectionId");
            params.put("sectionId", filter.getSectionId());
        }
        // category TODO
        if (filter.getCategoryId() != null) {
            selectQueryBuilder.append(" inner join ArticleCategoryEntity ace on ace.articleId = a.id  ");
            countQueryBuilder.append(" inner join ArticleCategoryEntity ace on ace.articleId = a.id  ");

            builder.append(" and  ace.categoryId =:categoryId");
            params.put("categoryId", filter.getCategoryId());
        }
        if (filter.getPublishedDateFrom() != null && filter.getPublishedDateTo() != null) {
            builder.append(" and a.publishDate between :publishedDateFrom and :publishedDateTo ");
            params.put("publishedDateTo", filter.getPublishedDateTo());
            params.put("publishedDateFrom", filter.getPublishedDateFrom());
        } else if (filter.getPublishedDateFrom() != null) {
            builder.append(" and a.publishDate > :publishedDateFrom ");
            params.put("publishedDateFrom", filter.getPublishedDateFrom());
        } else if (filter.getPublishedDateTo() != null) {
            builder.append(" and a.publishDate < :publishedDateTo ");
            params.put("publishedDateTo", filter.getPublishedDateTo());
        }

        if (filter.getCreatedDateTo() != null && filter.getCreatedDateFrom() != null) {
            builder.append(" and a.createdDate between :createdDateFrom and :createdDateTo ");
            params.put("createdDateTo", filter.getCreatedDateTo());
            params.put("createdDateFrom", filter.getCreatedDateFrom());
        } else if (filter.getCreatedDateFrom() != null) {
            builder.append(" and a.createdDate > :createdDateFrom ");
            params.put("createdDateFrom", filter.getCreatedDateFrom());
        } else if (filter.getCreatedDateTo() != null) {
            builder.append(" and a.createdDate < :createdDateTo ");
            params.put("createdDateTo", filter.getCreatedDateTo());
        }

        if (filter.getStatus() != null) {
            builder.append(" and  a.status = :status");
            params.put("status", filter.getStatus());
        }

        if (filter.getPublisherId() != null) {
            builder.append(" and  a.publisher.id = :publisherId");
            params.put("publisherId", filter.getPublisherId());
        }

        if (filter.getModeratorId() != null) {
            builder.append(" and  a.moderator.id = :moderatorId");
            params.put("moderatorId", filter.getModeratorId());
        }

        selectQueryBuilder.append(builder);
        countQueryBuilder.append(builder);

        // select query
        Query selectQuery = entityManager.createQuery(selectQueryBuilder.toString());
        selectQuery.setFirstResult((page) * size); // 50
        selectQuery.setMaxResults(size); // 30
        params.forEach(selectQuery::setParameter);

        List<Object[]> articleList = selectQuery.getResultList();

        // totalCount query
        Query countQuery = entityManager.createQuery(countQueryBuilder.toString());
        params.forEach(countQuery::setParameter);

        Long totalElements = (Long) countQuery.getSingleResult();

        return new FilterResultDTO<Object[]>(articleList, totalElements);
    }



}
