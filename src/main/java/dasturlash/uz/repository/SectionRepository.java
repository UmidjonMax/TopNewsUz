package dasturlash.uz.repository;

import dasturlash.uz.entity.CategoryEntity;
import dasturlash.uz.entity.SectionEntity;
import dasturlash.uz.mapper.SectionMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepository extends CrudRepository<SectionEntity, Integer> {
    @Modifying
    @Transactional
    @Query("update SectionEntity set visible = false where id =?1")
    int updateVisible(Integer id);

    @Query("from SectionEntity where visible = true order by orderNumber asc")
    List<SectionEntity> findAllByOrderSorted();

    Optional<SectionEntity> findByKey(String key);

    Optional<SectionEntity> findByIdAndVisibleIsTrue(Integer id);

    @Query("SELECT c.id AS id, " +
            "CASE :lang " +
            "   WHEN 'UZ' THEN c.nameUz " +
            "   WHEN 'RU' THEN c.nameRu " +
            "   WHEN 'EN' THEN c.nameEn " +
            "END AS name, " +
            "c.orderNumber AS orderNumber, " +
            "c.key AS sectionKey " +
            "FROM SectionEntity c " +
            "WHERE c.visible = true order by orderNumber asc")
    List<SectionMapper> getByLang(@Param("lang") String lang);

    @Query("SELECT s.id AS id, " +
            "CASE :lang " +
            "   WHEN 'UZ' THEN s.nameUz " +
            "   WHEN 'RU' THEN s.nameRu " +
            "   WHEN 'EN' THEN s.nameEn " +
            "END AS name, " +
            "s.key AS sectionKey " +
            "FROM SectionEntity s " +
            " inner join ArticleSectionEntity  ase on ase.sectionId = s.id " +
            "WHERE ase.articleId = :articleId and s.visible = true order by s.orderNumber asc")
    List<SectionMapper> getSectionListByArticleIdAndLang(@Param("articleId") String articleId, @Param("lang") String lang);

}
