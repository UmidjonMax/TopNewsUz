package dasturlash.uz.repository;

import dasturlash.uz.entity.CategoryEntity;
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


}
