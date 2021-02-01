package com.example.server.service;

import com.example.server.dtos.AuthorDto;
import com.example.server.entity.Author;
import com.example.server.exception.ResourceCreateException;
import com.example.server.exception.ResourceNotFoundException;
import com.example.server.mapper.AuthorMapper;
import com.example.server.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public List<Author> getAll() {
        return this.authorRepository.findAll();
    }

    public Author addAuthor(AuthorDto dto) {
        if (dto.getId() != null){
            throw new ResourceCreateException(dto.getId());
        }
        Author author = authorMapper.dtoToAuthor(dto);
        return this.authorRepository.save(author);
    }

    public void deleteAuthor(Long id) {
        Author author = this.authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("author", "id", id));
        this.authorRepository.delete(author);

    }

    public AuthorDto getById(long id){
        Author author = this.authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("book", "id", id));
        return this.authorMapper.authorToDto(author);
    }
}
