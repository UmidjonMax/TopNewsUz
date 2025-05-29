package dasturlash.uz.repository;

import dasturlash.uz.dto.SectionDTO;
import dasturlash.uz.entity.ArticleSectionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArticleSectionRepository extends CrudRepository<ArticleSectionEntity, Integer> {
    @Query("select section from ArticleSectionEntity where articleId =?1")
    List<SectionDTO> findSectionsByArticleId(UUID articleId);

    @Transactional
    @Modifying
    @Query("DELETE from ArticleSectionEntity where articleId =?1 and sectionId =?2")
    void deleteByArticleIdAndSectionId(UUID articleId,Integer sectionId);
}
