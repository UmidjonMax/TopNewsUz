package dasturlash.uz.repository;

import dasturlash.uz.entity.CategoryEntity;
import dasturlash.uz.mapper.CategoryMapper;
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
public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {
    @Query("from CategoryEntity where visible = true order by orderNumber asc")
    List<CategoryEntity> findAllByOrderSorted();


    @Modifying
    @Transactional
    @Query("update CategoryEntity set visible = false where id = ?1")
    int updateVisible( Integer id);

    Optional<CategoryEntity> findByKey(String key);

    Optional<CategoryEntity> findByIdAndVisibleIsTrue(Integer id);

    @Query("SELECT s.id AS id, " +
            "CASE :lang " +
            "   WHEN 'UZ' THEN s.nameUz " +
            "   WHEN 'RU' THEN s.nameRu " +
            "   WHEN 'EN' THEN s.nameEn " +
            "END AS name, " +
            "s.key AS key " +
            "FROM CategoryEntity s " +
            " inner join ArticleSectionEntity  ase on ase.sectionId = s.id " +
            "WHERE ase.articleId = :articleId and s.visible = true order by s.orderNumber asc")
    List<CategoryMapper> getCategoryListByArticleIdAndLang(@Param("articleId") String articleId, @Param("lang") String lang);


}
