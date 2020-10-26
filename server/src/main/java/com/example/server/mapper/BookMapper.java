package com.example.server.mapper;

import com.example.server.dtos.BookDto;
import com.example.server.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    private final CategoryMapper categoryMapper;

    @Autowired
    public BookMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public BookDto bookToDto(Book book){
        return new BookDto(book.getId(),
                            book.getTitle(),
                            book.getDescription(),
                            book.getImageUrl(),
                            book.getAuthor().getId(),
                            book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName(),
                            categoryMapper.categoriesToDto(book.getCategories()));
    }

    public List<BookDto> booksToDto(List<Book> books){
        return books.stream().map(this::bookToDto).collect(Collectors.toList());
    }
}
