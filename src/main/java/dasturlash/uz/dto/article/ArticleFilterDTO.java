package dasturlash.uz.dto.article;

import dasturlash.uz.enums.ArticleStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class ArticleFilterDTO {
    private String title;
    private Integer regionId;
    private Integer categoryId;
    private Integer sectionId;
    private LocalDate createdDateFrom;
    private LocalDate createdDateTo;
    private LocalDate publishedDateFrom;
    private LocalDate publishedDateTo;
    private Integer publisherId;
    private Integer moderatorId;
    private ArticleStatusEnum status;

    public LocalDate getPublishedDateFrom() {
        return publishedDateFrom;
    }

    public void setPublishedDateFrom(LocalDate publishedDateFrom) {
        this.publishedDateFrom = publishedDateFrom;
    }

    public LocalDate getPublishedDateTo() {
        return publishedDateTo;
    }

    public void setPublishedDateTo(LocalDate publishedDateTo) {
        this.publishedDateTo = publishedDateTo;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public Integer getModeratorId() {
        return moderatorId;
    }

    public void setModeratorId(Integer moderatorId) {
        this.moderatorId = moderatorId;
    }

    public ArticleStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ArticleStatusEnum status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
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
}
