package dasturlash.uz.service;

import dasturlash.uz.entity.ArticleEntity;
import dasturlash.uz.entity.ArticleLikeEntity;
import dasturlash.uz.entity.ProfileEntity;
import dasturlash.uz.repository.ArticleLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ArticleLikeService {
    @Autowired
    private ArticleLikeRepository articleLikeRepository;

    @Autowired
    private ArticleService articleService;

    public void likeOrUnlike(String articleId, Integer profileId) {
        Optional<ArticleLikeEntity> optional = articleLikeRepository.findByArticleIdAndProfileId(articleId, profileId);
        if (optional.isPresent()) {
            // If already liked, remove (unlike)
            articleLikeRepository.delete(optional.get());
        } else {
            // Like new
            ArticleLikeEntity entity = new ArticleLikeEntity();
            entity.setArticleId(articleId);
            entity.setProfileId(profileId);
            entity.setCreatedDate(LocalDateTime.now());
            articleLikeRepository.save(entity);
        }
    }

    public long getLikeCount(String articleId) {
        return articleLikeRepository.countByArticleId(articleId);
    }
}
