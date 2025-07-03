package dasturlash.uz.dto.article;

import dasturlash.uz.dto.CategoryDTO;
import dasturlash.uz.dto.SectionDTO;
import jakarta.validation.Valid;

import java.util.List;

public class ArticleCreateDTO { // TODO put validation
    @Valid()
    private String title;
    private String description;
    private String content;
    private String imageId;
    private Integer regionId;
    private Integer readTime; // in second
    //    private List<Integer> categoryId;    // [ 1,2,3,4]
    private List<CategoryDTO> categoryList; // [ {id:1}, {id:2},{id:3},{id:4}]
    private List<SectionDTO> sectionList; // [ {id:1}, {id:2},{id:3},{id:4}]

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

    public Integer getReadTime() {
        return readTime;
    }

    public void setReadTime(Integer readTime) {
        this.readTime = readTime;
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