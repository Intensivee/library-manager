package com.example.server.controller;

import com.example.server.dtos.BookProjection;
import com.example.server.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@CrossOrigin("http://localhost:4200")
@RestController()
@RequestMapping("dto")
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
        List<BookProjection> books = bookRepository.getDtoBooks();
        if (books.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(books);
    }

    @GetMapping(value = "/paged")
    public ResponseEntity<?> getBooksDtoPaged(Pageable pageable) {
        Page<BookProjection> booksPaged = bookRepository.getDtoBooksPaged(pageable);
        if (booksPaged.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(booksPaged);
    }

}

