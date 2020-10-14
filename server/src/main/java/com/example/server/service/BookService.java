package com.example.server.service;

import com.example.server.dtos.BookDto;
import com.example.server.dtos.BookProjection;
import com.example.server.exception.BookNotFoundException;
import com.example.server.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryService categoryService;
    private Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    public BookService(BookRepository bookRepository, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.categoryService = categoryService;
    }

    public BookDto getBookDtoById(long id){
        BookProjection projection = bookRepository.getProjectionById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return this.mapProjectionToDto(projection);
    }

    public List<BookDto> getBooksDto(){
        List<BookProjection> projections = bookRepository.getProjections();
        if(projections.isEmpty()){
            throw new BookNotFoundException();
        }
        return projections.stream().map(this::mapProjectionToDto).collect(Collectors.toList());
    }

    public Page<BookDto> getBooksDtoPaged(Pageable pageable) {
        Page<BookProjection> projections = bookRepository.getProjectionsPaged(pageable);
        if(projections.isEmpty()){
            throw new BookNotFoundException();
        }
        // build-in Page.map method
        return projections.map(this::mapProjectionToDto);
    }

    public Page<BookDto> getBooksDtoByCategory(Long id, Pageable pageable){
        Page<BookProjection> projections = bookRepository.getProjectionsByCategory(id, pageable);
        if(projections.isEmpty()){
            throw new BookNotFoundException();
        }
        return projections.map(this::mapProjectionToDto);
    }

    public List<BookDto> getBooksDtoByTitle(String title){
        List<BookProjection> projections = bookRepository.getProjectionsByTitle(title);
        if(projections.isEmpty()){
            throw new BookNotFoundException(title);
        }
        return projections.stream().map(this::mapProjectionToDto).collect(Collectors.toList());
    }

    public List<BookDto> getBooksDtoByAuthorId(Long id) {
        List<BookProjection> projections = bookRepository.getProjectionsByAuthorId(id);
        if(projections.isEmpty()){
            throw new BookNotFoundException();
        }
        return projections.stream().map(this::mapProjectionToDto).collect(Collectors.toList());
    }

    public BookDto mapProjectionToDto(BookProjection projection){
        return new BookDto(projection,categoryService.getCategoriesByBookId(projection.getId()));
    }
}
