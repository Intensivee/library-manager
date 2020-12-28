package com.example.server.controller;


import com.example.server.dtos.CategoryDto;
import com.example.server.entity.Category;
import com.example.server.payload.PostResponse;
import com.example.server.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController()
@RequestMapping("categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        // adding href link to each element
        List<EntityModel<?>> categories =  categoryService.getCategories().stream()
                .map(category -> EntityModel.of(category, linkTo(CategoryController.class).slash(category.getId()).withSelfRel()))
                .collect(Collectors.toList());

        // wrapping to collection with href link
        CollectionModel<EntityModel<?>> collection = new CollectionModel<>(categories).add(linkTo(CategoryController.class).withSelfRel());
        return ResponseEntity.ok(collection);
    }

    @PostMapping
    public ResponseEntity<PostResponse> addCategory(@Valid @RequestBody CategoryDto categoryDto){
        Category category = this.categoryService.createCategory(categoryDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(category.getId())
                .toUri();

        return ResponseEntity.created(location).body(new PostResponse(category.getId(), location));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id){
        this.categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/search/isEmpty/{id}")
    public Boolean isEmpty(@PathVariable("id") Long id) {
        return this.categoryService.containNoBooks(id);
    }
}
