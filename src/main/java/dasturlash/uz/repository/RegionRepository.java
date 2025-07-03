package dasturlash.uz.repository;

import dasturlash.uz.entity.RegionEntity;
import dasturlash.uz.mapper.RegionMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository extends CrudRepository<RegionEntity, Integer> {
    @Query("from RegionEntity where visible = true order by orderNumber asc")
    List<RegionEntity> findAllSorted();

    Optional<RegionEntity> findAllByIdAndVisibleIsTrue(Integer id);

    Optional<RegionEntity> findAllByKey(String key);

    @Modifying
    @Transactional
    @Query("update RegionEntity set visible = false where id =?1")
    int updateVisible(Integer id);

    @Query("SELECT c.id AS id, " +
            "CASE :lang " +
            "   WHEN 'UZ' THEN c.nameUz " +
            "   WHEN 'RU' THEN c.nameRu " +
            "   WHEN 'EN' THEN c.nameEn " +
            "END AS name, " +
            "c.orderNumber AS orderNumber, " +
            "c.key AS regionKey " +
            "FROM RegionEntity c " +
            "WHERE c.visible = true order by orderNumber asc")
    List<RegionMapper> getByLang(@Param("lang") String lang);

    @Query("SELECT c.id AS id, " +
            "CASE :lang " +
            "   WHEN 'UZ' THEN c.nameUz " +
            "   WHEN 'RU' THEN c.nameRu " +
            "   WHEN 'EN' THEN c.nameEn " +
            "END AS name, " +
            "c.key AS regionKey " +
            "FROM RegionEntity c " +
            "WHERE c.visible = true and c.id = :id")
    Optional<RegionMapper> getByIdAndLang(@Param("id") Integer id, @Param("lang") String lang);


}
