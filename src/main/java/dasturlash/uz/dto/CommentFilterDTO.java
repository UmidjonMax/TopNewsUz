package dasturlash.uz.dto;

import java.time.LocalDate;

public class CommentFilterDTO {
    private String id;
    private LocalDate createdDateFrom;
    private LocalDate createdDateTo;
    private Integer profileId;
    private String articleId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getCreatedDateFrom() {
        return createdDateFrom;
    }

    public void setCreatedDateFrom(LocalDate createdDateFrom) {
        this.createdDateFrom = createdDateFrom;
    }

    public LocalDate getCreatedDateTo() {
        return createdDateTo;
    }

    public void setCreatedDateTo(LocalDate createdDateTo) {
        this.createdDateTo = createdDateTo;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }
}
