package dasturlash.uz.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dasturlash.uz.dto.article.ArticleDTO;
import dasturlash.uz.dto.profile.ProfileDTO;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO {
    private String id;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private ProfileDTO profile;
    private ArticleDTO article;
    private CommentDTO reply;
    private Long likeCount;
    private Long disLikeCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public ProfileDTO getProfile() {
        return profile;
    }

    public void setProfile(ProfileDTO profile) {
        this.profile = profile;
    }

    public ArticleDTO getArticle() {
        return article;
    }

    public void setArticle(ArticleDTO article) {
        this.article = article;
    }

    public CommentDTO getReply() {
        return reply;
    }

    public void setReply(CommentDTO reply) {
        this.reply = reply;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public Long getDisLikeCount() {
        return disLikeCount;
    }

    public void setDisLikeCount(Long disLikeCount) {
        this.disLikeCount = disLikeCount;
    }
}
