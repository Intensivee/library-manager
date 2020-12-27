package com.example.server.service;

import com.example.server.dtos.AuthorDto;
import com.example.server.entity.Author;
import com.example.server.exception.ObjectCreateException;
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
            throw new ObjectCreateException(dto.getId());
        }
        Author author = authorMapper.dtoToAuthor(dto);
        return this.authorRepository.save(author);
    }
}
