package com.example.server.service;

import com.example.server.dtos.CategoryDto;
import com.example.server.entity.Category;
import com.example.server.exception.BookNotFoundException;
import com.example.server.exception.CategoryNotFoundException;
import com.example.server.mapper.CategoryMapper;
import com.example.server.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public boolean containNoBooks(Long id){
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        return category.getBooks().isEmpty();
    }

    public boolean isPresent(Long id){
        return this.categoryRepository.findById(id).isPresent();
    }

    public Category getCategory(Long id){
        return this.categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
    }

    public List<CategoryDto> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty()){
            throw new BookNotFoundException();  // TODO: generic exceptions
        }
        return this.categoryMapper.categoriesToDto(categories);
    }

    public Category createCategory(CategoryDto dto) {
        return this.categoryRepository.save(categoryMapper.dtoToCategory(dto));
    }

    public void deleteCategory(Long id) {
        Category category = this.getCategory(id);
        this.categoryRepository.delete(category);
    }
}
