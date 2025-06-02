package dasturlash.uz.repository;

import dasturlash.uz.entity.ArticleEntity;
import dasturlash.uz.enums.ArticleStatusEnum;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ArticleRepository extends CrudRepository<ArticleEntity, UUID>, PagingAndSortingRepository<ArticleEntity, UUID> {
    Optional<ArticleEntity> findByTitleAndVisibleIsTrue(String title);

    Optional<ArticleEntity> findByIdAndVisibleIsTrue(UUID id);

    @Modifying
    @Transactional
    @Query("update ArticleEntity set visible = false where id = ?1")
    int updateVisible(UUID id);

    @Modifying
    @Transactional
    @Query("update ArticleEntity set status = ?2 where id = ?1")
    int updateStatus(UUID id, ArticleStatusEnum status);

}
