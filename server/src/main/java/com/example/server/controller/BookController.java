package com.example.server.controller;

import com.example.server.dtos.BookDto;
import com.example.server.entity.Book;
import com.example.server.payload.CreateResponse;
import com.example.server.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController()
@RequestMapping("books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        // adding href link to each element
        List<EntityModel<?>> books =  bookService.getAll().stream()
                .map(book -> EntityModel.of(book, linkTo(BookController.class).slash(book.getId()).withSelfRel()))
                .collect(Collectors.toList());

        // wrapping to collection with href link
        CollectionModel<EntityModel<?>> collection = new CollectionModel<>(books).add(linkTo(BookController.class).withSelfRel());
        return ResponseEntity.ok(collection);
    }

    @PostMapping
    public ResponseEntity<?> addBook(@Valid @RequestBody BookDto bookDto){
        Book book = this.bookService.createBook(bookDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(book.getId())
                .toUri();

        return ResponseEntity.created(location).body(new CreateResponse(book.getId(), location));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<?>> getById(@PathVariable("id") Long id) {
        BookDto book = bookService.getById(id);
        EntityModel<?> entityModel = EntityModel.of(book, linkTo(BookController.class).slash(id).withSelfRel());
        return ResponseEntity.ok(entityModel);
    }

    @GetMapping(value = "/paged")
    public ResponseEntity<?> getAll(Pageable pageable, PagedResourcesAssembler<BookDto> assembler) {
        Page<BookDto> books = bookService.getAll(pageable);
        return ResponseEntity.ok(assembler.toModel(books));
    }

    @GetMapping(value = "/search/findByCategoryId/{id}")
    public ResponseEntity<?> getByCategoryId(@PathVariable("id") Long id,
                                             Pageable pageable,
                                             PagedResourcesAssembler<BookDto> assembler){
        Page<BookDto> books = bookService.getByCategory(id, pageable);
        return ResponseEntity.ok(assembler.toModel(books));
    }

    @GetMapping(value = "/search/findByTitle/{title}")
    public ResponseEntity<CollectionModel<EntityModel<?>>> getByTitle(@PathVariable("title") String title){

        List<EntityModel<?>> books = bookService.getByTitle(title).stream()
                .map(book -> EntityModel.of(book, linkTo(BookController.class).slash(book.getId()).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<?>> collection = new CollectionModel<>(books).add(linkTo(BookController.class).withSelfRel());
        return ResponseEntity.ok(collection);
    }

    @GetMapping(value = "/search/findByAuthorId/{id}")
    public ResponseEntity<?> getByAuthorId(@PathVariable("id") long id){
        List<EntityModel<?>> books =  bookService.getByAuthorId(id).stream()
                .map(book -> EntityModel.of(book, linkTo(BookController.class).slash(book.getId()).withSelfRel()))
                .collect(Collectors.toList());

        // wrapping to collection with href link
        CollectionModel<EntityModel<?>> collection = new CollectionModel<>(books).add(
                linkTo(methodOn(BookController.class).getByAuthorId(id)).withSelfRel());
        return ResponseEntity.ok(collection);
    }

}

