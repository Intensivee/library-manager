package com.example.server.mapper;

import com.example.server.dtos.CategoryDto;
import com.example.server.entity.Category;
import com.example.server.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    private final CategoryService categoryService;

    @Autowired
    public CategoryMapper(@Lazy CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public CategoryDto categoryToDto(Category category){
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }

    public List<CategoryDto> categoriesToDto(List<Category> categories){
        return categories.stream().map(this::categoryToDto).collect(Collectors.toList());
    }

    public Category dtoToCategory(CategoryDto dto) {
        return  new Category(
                dto.getId(),
                dto.getName(),
                dto.getId() != null ? this.categoryService.getById(dto.getId()).getBooks() : null
        );
    }

    public List<Category> dtoToCategories(List<CategoryDto> categoryDtos){
        return categoryDtos.stream().map(this::dtoToCategory).collect(Collectors.toList());
    }
}
