package com.example.server.controller;

import com.example.server.dtos.AuthorDto;
import com.example.server.entity.Author;
import com.example.server.payload.PostResponse;
import com.example.server.service.AuthorService;
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
@RequestMapping("authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        List<EntityModel<?>> authors =  authorService.getAll().stream()
                .map(book -> EntityModel.of(book, linkTo(BookController.class).slash(book.getId()).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<?>> collection = new CollectionModel<>(authors).add(linkTo(AuthorController.class).withSelfRel());
        return ResponseEntity.ok(collection);
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody AuthorDto authorDto){
        Author author = this.authorService.create(authorDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(author.getId())
                .toUri();

        return ResponseEntity.created(location).body(new PostResponse(author.getId(), location));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        AuthorDto author = this.authorService.getById(id);
        EntityModel<?> entityModel = EntityModel.of(author, linkTo(BookController.class).slash(id).withSelfRel());
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable("id") Long id) {
        this.authorService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
