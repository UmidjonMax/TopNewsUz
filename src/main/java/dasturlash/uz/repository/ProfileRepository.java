package dasturlash.uz.repository;

import dasturlash.uz.entity.ProfileEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.expression.spel.ast.OpAnd;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer>, PagingAndSortingRepository<ProfileEntity, Integer> {
    Optional<ProfileEntity> findByUsernameAndVisibleIsTrue(String username);
    Optional<ProfileEntity> findByIdAndVisibleIsTrue(Integer id);

    @Query("from ProfileEntity where visible = true order by createdDate asc")
    List<ProfileEntity> findAllSorted();

    @Modifying
    @Transactional
    @Query("update ProfileEntity set visible = false where id = ?1")
    int updateVisible(Integer id);
}
