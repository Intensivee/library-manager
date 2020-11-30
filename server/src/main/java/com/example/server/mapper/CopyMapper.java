package com.example.server.mapper;

import com.example.server.dtos.CopyDto;
import com.example.server.entity.Copy;
import com.example.server.repository.BookRepository;
import com.example.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CopyMapper {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public CopyMapper(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public CopyDto copyToDto(Copy copy){
        return new CopyDto(
                copy.getId(),
                copy.getPages(),
                copy.getBorrowed(),
                copy.getBorrowDate(),
                copy.getReturnDate(),
                (copy.getBook() == null) ? null : copy.getBook().getId(),
                (copy.getUser() == null) ? null : copy.getUser().getId());
    }

    public Copy dtoToCopy(CopyDto copyDto){
        return new Copy(
                copyDto.getId(),
                copyDto.getPages(),
                copyDto.getBorrowed(),
                copyDto.getBorrowDate(),
                copyDto.getReturnDate(),
                (copyDto.getBookId() == null) ? null: bookRepository.getOne(copyDto.getBookId()),
                (copyDto.getUserId() == null) ? null: userRepository.getOne(copyDto.getUserId()));
    }

    public List<CopyDto> copiesToDto(List<Copy> copies){
        return copies.stream().map(this::copyToDto).collect(Collectors.toList());
    }

    public List<Copy> dtoToCopies(List<CopyDto> dtoList){
        return dtoList.stream().map(this::dtoToCopy).collect(Collectors.toList());
    }
}
