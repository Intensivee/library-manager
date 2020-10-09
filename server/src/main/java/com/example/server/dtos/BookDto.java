package com.example.server.dtos;

import org.springframework.hateoas.server.core.Relation;

import java.util.List;

@Relation
public class BookDto implements BookProjection {

    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private Long authorId;
    private String authorName;
    private List<CategoryDto> categories;

    public BookDto(BookProjection projection, List<CategoryDto> categories) {
        this.id = projection.getId();
        this.title = projection.getTitle();
        this.description = projection.getDescription();
        this.imageUrl = projection.getImageUrl();
        this.authorId = projection.getAuthorId();
        this.authorName = projection.getAuthorName();
        this.categories = categories;
    }

    public BookDto(Long id, String title, String description, String imageUrl, Long authorId, String authorName, List<CategoryDto> categories) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.authorId = authorId;
        this.authorName = authorName;
        this.categories = categories;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Override
    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }
}
