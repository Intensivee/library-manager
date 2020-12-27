package com.example.server.mapper;

import com.example.server.dtos.BookDto;
import com.example.server.entity.Book;
import com.example.server.repository.AuthorRepository;
import com.example.server.repository.CategoryRepository;
import com.example.server.repository.CopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    private final CategoryMapper categoryMapper;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final CopyRepository copyRepository;

    @Autowired
    public BookMapper(CategoryMapper categoryMapper, AuthorRepository authorRepository, CategoryRepository categoryRepository, CopyRepository copyRepository) {
        this.categoryMapper = categoryMapper;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.copyRepository = copyRepository;
    }

    public BookDto bookToDto(Book book){
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getDescription(),
                book.getImageUrl(),
                book.getAuthor().getId(),
                String.format("%s %s", book.getAuthor().getFirstName(), book.getAuthor().getLastName()),
                categoryMapper.categoriesToDto(book.getCategories()));
    }

    public List<BookDto> booksToDto(List<Book> books){
        return books.stream().map(this::bookToDto).collect(Collectors.toList());
    }

    public Book dtoToBook(BookDto bookDto) {

        Book book = new Book(
                bookDto.getId(),
                bookDto.getTitle(),
                bookDto.getDescription(),
                bookDto.getImageUrl(),
                this.authorRepository.getOne(bookDto.getAuthorId()),
                bookDto.getCategories().stream()
                        .map(dto -> this.categoryRepository.getOne(dto.getId()))
                        .collect(Collectors.toList()),
                bookDto.getId() != null ? this.copyRepository.findByBookId(bookDto.getId()) : null
        );

        // appending newly created book to each category (id doesn't happen automatically)
        book.getCategories().forEach(category -> {
            if (!category.getBooks().contains(book)) {
                category.addNewBook(book);
            }
        });
        return book;
    }

    public List<Book> dtoToBooks(List<BookDto> dto){
        return dto.stream().map(this::dtoToBook).collect(Collectors.toList());
    }
}
