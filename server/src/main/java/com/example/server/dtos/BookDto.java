package com.example.server.dtos;

import org.hibernate.validator.constraints.URL;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Relation
public class BookDto {

    private Long id;
    @Size(min = 2, max = 30, message = "Title must be between 2 and 30 characters")
    private String title;
    @Size(min = 1, max = 400, message = "Description must be between 1 and 400 characters")
    private String description;
    @URL(message = "image must be url!")
    private String imageUrl;
    @NotNull(message = "author must be selected!")
    private Long authorId;
    private String authorName;
    @NotNull(message = "categories must be selected!")
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
