package com.example.server.dtos;

import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Relation
public class BookDto {

    private Long id;
    @NotBlank(message = "title can not be empty!")
    private String title;
    @NotBlank(message = "description can not be empty!")
    private String description;
    @NotBlank(message = "image url can not be empty!")
    private String imageUrl;
    @NotBlank(message = "author must be selected!")
    private Long authorId;
    private String authorName;
    @NotBlank(message = "categories must be selected!")
    private List<CategoryDto> categories;

    public BookDto() {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

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
