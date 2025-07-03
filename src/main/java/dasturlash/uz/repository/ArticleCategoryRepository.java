package dasturlash.uz.repository;

import dasturlash.uz.dto.CategoryDTO;
import dasturlash.uz.dto.SectionDTO;
import dasturlash.uz.entity.ArticleCategoryEntity;
import dasturlash.uz.entity.ArticleSectionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArticleCategoryRepository extends CrudRepository<ArticleCategoryEntity, String> {
    @Query("select categoryId from ArticleCategoryEntity where articleId =?1")
    List<Integer> getCategoryIdListByArticleId(String articleId);

    @Query("delete  from  ArticleCategoryEntity where articleId =?1 and categoryId =?2")
    void deleteByCategoryIdAndArticleId(String articleId, Integer categoryId);
}
