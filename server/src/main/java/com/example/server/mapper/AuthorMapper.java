package com.example.server.mapper;


import com.example.server.dtos.AuthorDto;
import com.example.server.entity.Author;
import com.example.server.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    private final BookRepository bookRepository;

    @Autowired
    public AuthorMapper(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Author dtoToAuthor(AuthorDto dto) {
        return new Author(
                dto.getId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getBirthDate(),
                dto.getMemoir(),
                dto.getImageUrl(),
                (dto.getId() == null) ? null: bookRepository.findByAuthorId(dto.getId())
        );
    }
}
