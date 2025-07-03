package dasturlash.uz.entity;

import dasturlash.uz.enums.ArticleLikeEmotion;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "article_like")
public class ArticleLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;
    @Column(name = "article_id")
    private String articleId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aricle_id", insertable = false, updatable = false)
    private ArticleEntity article;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Enumerated(EnumType.STRING)
    private ArticleLikeEmotion emotion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public ProfileEntity getProfile() {
        return profile;
    }

    public void setProfile(ProfileEntity profile) {
        this.profile = profile;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public ArticleEntity getArticle() {
        return article;
    }

    public void setArticle(ArticleEntity article) {
        this.article = article;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ArticleLikeEmotion getEmotion() {
        return emotion;
    }

    public void setEmotion(ArticleLikeEmotion emotion) {
        this.emotion = emotion;
    }
}
