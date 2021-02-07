package com.example.server.service;

import com.example.server.dtos.BookDto;
import com.example.server.entity.Book;
import com.example.server.exception.ResourceCreateException;
import com.example.server.exception.ResourceNotFoundException;
import com.example.server.mapper.BookMapper;
import com.example.server.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public BookDto getById(long id){
        return bookRepository.findById(id)
                .map(this.bookMapper::bookToDto)
                .orElseThrow(() -> new ResourceNotFoundException("book", "id", id));
    }

    public List<BookDto> getAll(){
        List<Book> books = bookRepository.findAll();

        if(books.isEmpty()){
            throw new ResourceNotFoundException("books");
        }
        return this.bookMapper.booksToDto(books);
    }

    public Page<BookDto> getAll(Pageable pageable) {
        Page<Book> books = bookRepository.findAll(pageable);

        if(books.isEmpty()){
            throw new ResourceNotFoundException("books");
        }
        return books.map(bookMapper::bookToDto);
    }

    public Page<BookDto> getByCategory(Long id, Pageable pageable){
        Page<Book> books = bookRepository.findByCategoriesId(id, pageable);

        if(books.isEmpty()){
            throw new ResourceNotFoundException("books", "categoryId", id);
        }
        return books.map(bookMapper::bookToDto);
    }

    public List<BookDto> getByTitle(String title){
        List<Book> books = bookRepository.findFirst10ByTitleContaining(title);

        if(books.isEmpty()){
            throw new ResourceNotFoundException("book", "title", title);
        }
        return this.bookMapper.booksToDto(books);
    }

    public List<BookDto> getByAuthorId(Long id) {
        List<Book> books = bookRepository.findByAuthorId(id);

        if(books.isEmpty()){
            throw new ResourceNotFoundException("books", "authorId", id);
        }
        return this.bookMapper.booksToDto(books);
    }

    public Book createBook(BookDto bookDto) {
        if (bookDto.getId() != null) {
            throw new ResourceCreateException(bookDto.getId());
        }
        return this.bookRepository.save(this.bookMapper.dtoToBook(bookDto));
    }

    public void deleteBook(Long id) {
        Book book = this.bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("book", "id", id));
        this.bookRepository.delete(book);
    }
}
