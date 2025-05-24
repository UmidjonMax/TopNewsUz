package dasturlash.uz.repository;

import dasturlash.uz.entity.CategoryEntity;
import dasturlash.uz.entity.SectionEntity;
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
}
