package dasturlash.uz.repository;

import dasturlash.uz.entity.RegionEntity;
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
}
