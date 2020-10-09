package com.example.server.service;

import com.example.server.dtos.CategoryDto;
import com.example.server.dtos.CategoryProjection;
import com.example.server.exception.CategoryNotFoundException;
import com.example.server.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDto> findCategoryDtosByBookId(long id){
        List<CategoryProjection> categories = this.categoryRepository.getDtoByBookId(id);
        if(categories.isEmpty()){
            throw new CategoryNotFoundException(id);
        }
        return categories.stream().map(this::mapProjectionToDto).collect(Collectors.toList());
    }

    public CategoryDto mapProjectionToDto(CategoryProjection projection){
        return new CategoryDto(projection);
    }
}
