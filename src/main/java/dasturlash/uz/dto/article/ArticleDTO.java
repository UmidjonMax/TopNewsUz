package dasturlash.uz.dto.article;

import dasturlash.uz.dto.AttachDTO;
import dasturlash.uz.dto.CategoryDTO;
import dasturlash.uz.dto.RegionDTO;
import dasturlash.uz.dto.SectionDTO;
import dasturlash.uz.enums.ArticleStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleDTO {
    private String id;
    private String title;
    private String description;
    private String content;
    private Long sharedCount;

    private String imageId;
    private AttachDTO image;
    private Integer regionId;
    private RegionDTO region;

    private Integer moderatorId;
    private Integer publisherId;

    private ArticleStatusEnum status;

    private Integer readTime; // in second
    private Integer viewCount; // in second
    private Long likeCount;
    private LocalDateTime publishedDate;
    private List<CategoryDTO> categoryList;
    private List<SectionDTO> sectionList;

    public RegionDTO getRegion() {
        return region;
    }

    public void setRegion(RegionDTO region) {
        this.region = region;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public AttachDTO getImage() {
        return image;
    }

    public void setImage(AttachDTO image) {
        this.image = image;
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

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Integer getModeratorId() {
        return moderatorId;
    }

    public void setModeratorId(Integer moderatorId) {
        this.moderatorId = moderatorId;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
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

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public List<CategoryDTO> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryDTO> categoryList) {
        this.categoryList = categoryList;
    }

    public List<SectionDTO> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<SectionDTO> sectionList) {
        this.sectionList = sectionList;
    }
}