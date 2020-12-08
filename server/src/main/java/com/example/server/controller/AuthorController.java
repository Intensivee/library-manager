package com.example.server.controller;

import com.example.server.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@CrossOrigin("http://localhost:4200")
@RestController()
@RequestMapping("authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @GetMapping
    public ResponseEntity<?> getAll() {
        // adding href link to each element
        List<EntityModel<?>> books =  authorService.getAll().stream()
                .map(book -> EntityModel.of(book, linkTo(BookController.class).slash(book.getId()).withSelfRel()))
                .collect(Collectors.toList());

        // wrapping to collection with href link
        CollectionModel<EntityModel<?>> collection = new CollectionModel<>(books).add(linkTo(BookController.class).withSelfRel());
        return ResponseEntity.ok(collection);
    }
}
