package com.example.server.controller;

import com.example.server.dtos.BookProjection;
import com.example.server.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@CrossOrigin("http://localhost:4200")
@RestController()
@RequestMapping("books")
public class BookController {

    private Logger logger = LoggerFactory.getLogger(BookController.class);
    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookDto(@PathVariable("id") Long id) {

        Link link = linkTo(BookController.class).slash(id).withSelfRel();
        Optional<BookProjection> book = bookRepository.getDtoBookById(id);
        if (book.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        EntityModel<?> entityModel = EntityModel.of(book, link);
        return ResponseEntity.ok(entityModel);
    }

    @GetMapping()
    public ResponseEntity<?> getBooksDto() {

        // adding href link to each element
        List<EntityModel<?>> books =  bookRepository.getDtoBooks().stream()
                .map(book -> EntityModel.of(book, linkTo(BookController.class).slash(book.getId()).withSelfRel()))
                .collect(Collectors.toList());

        if(books.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        // wrapping to collection with href link
        CollectionModel<EntityModel<?>> collection = new CollectionModel<>(books).add(linkTo(BookController.class).withSelfRel());
        return ResponseEntity.ok(collection);
    }

    @GetMapping(value = "/paged")
    public ResponseEntity<?> getBooksDtoPaged(Pageable pageable) {
        Page<BookProjection> booksPaged = bookRepository.getDtoBooksPaged(pageable);

        if (booksPaged.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

//  TODO : adding hateos deletes pageable details from json..
//        List<EntityModel<?>> books = booksPaged.stream()
//                .map(book -> EntityModel.of(book, linkTo(BookController.class).slash(book.getId()).withSelfRel()))
//                .collect(Collectors.toList());
//        CollectionModel<EntityModel<?>> collection = new CollectionModel<>(books);
//                .add(linkTo(methodOn(BookController.class).getBooksDtoPaged(pageable)).withSelfRel());

        return ResponseEntity.ok(booksPaged);
    }

}

