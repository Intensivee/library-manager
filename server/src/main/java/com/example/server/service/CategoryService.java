package com.example.server.service;

import com.example.server.dtos.CategoryDto;
import com.example.server.entity.Category;
import com.example.server.exception.ResourceCreateException;
import com.example.server.exception.ResourceNotFoundException;
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

    public boolean containNoBooks(Long id)  {
        return this.categoryRepository.findById(id)
                .map(category -> category.getBooks().isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("category", "id", id));
    }

    public List<CategoryDto> getAll() {
        List<Category> categories = categoryRepository.findAll();

        if(categories.isEmpty()){
            throw new ResourceNotFoundException("categories");
        }
        return this.categoryMapper.categoriesToDto(categories);
    }

    public Category create(CategoryDto dto) {
        if(dto.getId() != null) {
            throw new ResourceCreateException(dto.getId());
        }
        return this.categoryRepository.save(categoryMapper.dtoToCategory(dto));
    }

    public void deleteById(Long id) {
        Category category = this.getById(id);
        this.categoryRepository.delete(category);
    }

    public Category getById(Long id){
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("category", "id", id));
    }
}
