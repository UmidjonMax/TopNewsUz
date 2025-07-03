package dasturlash.uz.repository;

import dasturlash.uz.entity.ArticleLikeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ArticleLikeRepository extends CrudRepository<ArticleLikeEntity, String> {
    Optional<ArticleLikeEntity> findByArticleIdAndProfileId(String articleId, Integer profileId);

    long countByArticleId(String articleId);
}
