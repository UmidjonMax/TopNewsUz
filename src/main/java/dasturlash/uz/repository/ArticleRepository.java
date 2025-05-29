package dasturlash.uz.repository;

import dasturlash.uz.entity.ArticleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ArticleRepository extends CrudRepository<ArticleEntity, UUID>, PagingAndSortingRepository<ArticleEntity, UUID> {
    Optional<ArticleEntity> findByTitleAndVisibleIsTrue(String title);

}
