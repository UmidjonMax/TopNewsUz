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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "title", columnDefinition = "text")
    private String title;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "shared_count")
    private Long sharedCount;
    @Column(name = "like_count")
    private Long likeCount;

    @Column(name = "image_id")
    private String imageId;
    @ManyToOne(fetch = FetchType.LAZY) // buyerda OneToOne ham bo'lishi mumkun.
    @JoinColumn(name = "image_id", insertable = false, updatable = false)
    private AttachEntity image;

    @Column(name = "region_id")
    private Integer regionId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private RegionEntity region;

    @Column(name = "moderator_id")
    private Integer moderatorId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_id", insertable = false, updatable = false)
    private ProfileEntity moderator;

    @Column(name = "publisher_id")
    private Integer publisherId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", insertable = false, updatable = false)
    private ProfileEntity publisher;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ArticleStatusEnum status;

    @Column(name = "read_time")
    private Integer readTime; // in second

    @Column(name = "view_count")
    private Integer viewCount; // in second

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;

    //  (Bitta article bir-nechta Category-ga tegishli mumkun)
    // 1 - N
    // N - 1

    //  (Bitta article bir-nechta Section-da bo'lishi mumkun. Masalan Asosiy,Muharrir h.k.)


    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Long getSharedCount() {
        return sharedCount;
    }

    public void setSharedCount(Long sharedCount) {
        this.sharedCount = sharedCount;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public AttachEntity getImage() {
        return image;
    }

    public void setImage(AttachEntity image) {
        this.image = image;
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

    public Integer getModeratorId() {
        return moderatorId;
    }

    public void setModeratorId(Integer moderatorId) {
        this.moderatorId = moderatorId;
    }

    public ProfileEntity getModerator() {
        return moderator;
    }

    public void setModerator(ProfileEntity moderator) {
        this.moderator = moderator;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public ProfileEntity getPublisher() {
        return publisher;
    }

    public void setPublisher(ProfileEntity publisher) {
        this.publisher = publisher;
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

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
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
}