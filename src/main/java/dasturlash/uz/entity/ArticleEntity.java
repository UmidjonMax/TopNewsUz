package dasturlash.uz.entity;

import dasturlash.uz.enums.ArticleStatusEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "article")
public class ArticleEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String title;
    private String description;
    @Column(columnDefinition = "TEXT")
    private String content;
    private Integer shares;

    @Column(name = "region_id")
    private Integer regionId;
    @ManyToOne
    @JoinColumn(name = "region_id", updatable = false, insertable = false)
    private RegionEntity region;

    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne
    @JoinColumn(name = "profile_id", updatable = false, insertable = false)
    private ProfileEntity profile;

    @Enumerated(EnumType.STRING)
    private ArticleStatusEnum status = ArticleStatusEnum.NOT_PUBLISHED;
    @Column(name = "read_time")
    private Integer readTime;
    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;
    @Column(name = "published_date")
    private LocalDateTime publishedDate;
    private Boolean visible = true;
    private Integer views = 0;
    @OneToMany(mappedBy = "article")
    private List<ArticleCategoryEntity> categoryList;
    @OneToMany(mappedBy = "article")
    private List<ArticleSectionEntity> sectionList;

    public List<ArticleCategoryEntity> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<ArticleCategoryEntity> categoryList) {
        this.categoryList = categoryList;
    }

    public List<ArticleSectionEntity> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<ArticleSectionEntity> sectionList) {
        this.sectionList = sectionList;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getShares() {
        return shares;
    }

    public void setShares(Integer shares) {
        this.shares = shares;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public RegionEntity getRegion() {
        return region;
    }

    public void setRegion(RegionEntity region) {
        this.region = region;
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

    public ArticleStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ArticleStatusEnum status) {
        this.status = status;
    }

    public Integer getReadTime() {
        return readTime;
    }

    public void setReadTime(Integer readTime) {
        this.readTime = readTime;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }
}
