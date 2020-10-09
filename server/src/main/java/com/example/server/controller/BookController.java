package com.example.server.controller;

import com.example.server.dtos.BookDto;
import com.example.server.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@CrossOrigin("http://localhost:4200")
@RestController()
@RequestMapping("dtoBooks")
public class BookController {

    private final Logger logger = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<?>> getBookDto(@PathVariable("id") Long id) {
        BookDto book = bookService.getBookDtoById(id);
        EntityModel<?> entityModel = EntityModel.of(book, linkTo(BookController.class).slash(id).withSelfRel());
        return ResponseEntity.ok(entityModel);
    }

    @GetMapping()
    public ResponseEntity<?> getBooksDto() {
        // adding href link to each element
        List<EntityModel<?>> books =  bookService.getBooksDto().stream()
                .map(book -> EntityModel.of(book, linkTo(BookController.class).slash(book.getId()).withSelfRel()))
                .collect(Collectors.toList());

        // wrapping to collection with href link
        CollectionModel<EntityModel<?>> collection = new CollectionModel<>(books).add(linkTo(BookController.class).withSelfRel());
        return ResponseEntity.ok(collection);
    }

    @GetMapping(value = "/paged")
    public ResponseEntity<?> getBooksDtoPaged(Pageable pageable, PagedResourcesAssembler<BookDto> assembler) {
        Page<BookDto> books = bookService.getBooksDtoPaged(pageable);
        PagedModel<EntityModel<BookDto>> pageModel = assembler.toModel(books);
        return ResponseEntity.ok(pageModel);
    }

    @GetMapping(value = "/search/findByCategoryId/{id}")
    public ResponseEntity<?> getBooksPagedByCategoryId(@PathVariable("id") Long id,
                                                       Pageable pageable,
                                                       PagedResourcesAssembler<BookDto> assembler){
        Page<BookDto> books = bookService.getBooksDtoByCategory(id, pageable);
        return ResponseEntity.ok(assembler.toModel(books));
    }

    @GetMapping(value = "/search/findByTitle/{title}")
    public ResponseEntity<CollectionModel<EntityModel<?>>> getBooksPagedByCategoryId(@PathVariable("title") String title){

        List<EntityModel<?>> books = bookService.getBooksDtoByTitle(title).stream()
                .map(book -> EntityModel.of(book, linkTo(BookController.class).slash(book.getId()).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<?>> collection = new CollectionModel<>(books).add(linkTo(BookController.class).withSelfRel());
        return ResponseEntity.ok(collection);
    }

}

