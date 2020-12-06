package com.example.server.controller;


import com.example.server.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
@RestController()
@RequestMapping("categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/search/isEmpty/{id}")
    public Boolean isEmpty(@PathVariable("id") Long id) {
        return this.categoryService.isEmpty(id);
    }
}
