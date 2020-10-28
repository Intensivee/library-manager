package com.example.server.mapper;

import com.example.server.dtos.CategoryDto;
import com.example.server.entity.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public CategoryDto categoryToDto(Category category){
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }

    public List<CategoryDto> categoriesToDto(List<Category> categories){
        return categories.stream().map(this::categoryToDto).collect(Collectors.toList());
    }
}
