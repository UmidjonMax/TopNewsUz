package dasturlash.uz.repository;

import dasturlash.uz.entity.ArticleEntity;
import dasturlash.uz.enums.ArticleStatusEnum;
import dasturlash.uz.mapper.ArticleShortInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends CrudRepository<ArticleEntity, String>, PagingAndSortingRepository<ArticleEntity, String> {
    Optional<ArticleEntity> findByTitleAndVisibleIsTrue(String title);

    Optional<ArticleEntity> findByIdAndVisibleIsTrue(String id);

    @Query(value = "from ArticleEntity where status = 'PUBLISHED' and id in(select articleId from ArticleSectionEntity where sectionId = :sectionId) order by createdDate desc limit :n")
    List<ArticleShortInfo> findLastNArticlesBySectionId(@Param("sectionId") Integer sectionId, @Param("n") Integer n);

    @Query(value = "from ArticleEntity where status = 'PUBLISHED' and id not in ?1 order by createdDate desc limit 12")
    List<ArticleShortInfo> findLast12ArticlesExcept(List<String> articleIds);

    @Query(value = "from ArticleEntity where status = 'PUBLISHED' and id in(select articleId from ArticleCategoryEntity where categoryId = ?2) order by createdDate desc limit ?1")
    List<ArticleShortInfo> findLastNArticlesByCategoryId(Integer n, Integer sectionId);

    @Query(value = "from ArticleEntity where status = 'PUBLISHED' and regionId = :region order by createdDate desc limit :n")
    List<ArticleShortInfo> findLastNArticlesByRegionId(Integer n, Integer regionId);

    @Query("from ArticleEntity where lower(title) ilike :tag order by createdDate desc limit :limit")
    List<ArticleShortInfo> findByTag(String tag, Integer limit);

    @Query(value = "from ArticleEntity where status = 'PUBLISHED' and id in(select articleId from ArticleSectionEntity where sectionId = :sectionId) and id <> :id order by createdDate desc limit 4")
    List<ArticleShortInfo> findLast4ArticlesBySectionId(@Param("sectionId") Integer sectionId, @Param("id") String id);

    @Query(value = "from ArticleEntity where status = 'PUBLISHED' and id <> :id  order by viewCount desc limit :n")
    List<ArticleShortInfo> findMostReadArticle(@Param("id") String id);

    @Query(" select a.id as id, a.title as title, a.description as description, a.imageId as imageId, a.publishedDate as publishedDate " +
            " from  ArticleEntity a " +
            " inner join ArticleSectionEntity ac on ac.articleId = a.id " +
            " where ac.sectionId = ?1 and a.visible = true and a.status = 'PUBLISHED' " +
            " order by a.createdDate desc  limit ?2")
    List<ArticleShortInfo> getBySectionId(Integer sectionId, int limit);

    @Transactional
    @Modifying
    @Query("Update ArticleEntity set viewCount = viewCount + 1 where id =?1")
    int increaseViewCount(String articleId);

    // 14. Increase Share Count by Article Id
    @Transactional
    @Modifying
    @Query("Update ArticleEntity set sharedCount = sharedCount + 1 where id =?1")
    int increaseSharedCount(String articleId);


    // share count-ni increase qiladi va oxirgi qiymatni return qiladi.
    @Query(value = "UPDATE article SET shared_count = shared_count + 1 WHERE id = ?1 RETURNING shared_count", nativeQuery = true)
    int incrementSharedCountAndGet(String articleId);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set viewCount = viewCount+1")
    void changeViewCount(@Param("id") String id);

    @Transactional
    @Modifying
    @Query("Update  ArticleEntity set visible = false where id =?1")
    int delete(String articleId);

    @Transactional
    @Modifying
    @Query("Update ArticleEntity set status = ?2 where id =?1")
    int changeStatus(String articleId, ArticleStatusEnum status);

}
