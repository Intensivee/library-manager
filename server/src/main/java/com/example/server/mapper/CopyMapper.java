package com.example.server.mapper;

import com.example.server.dtos.CopyDto;
import com.example.server.entity.Copy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CopyMapper {

    public CopyDto copyToDto(Copy copy){
        return new CopyDto(
                copy.getId(),
                copy.getPages(),
                copy.isBorrowed(),
                copy.getBorrowDate(),
                copy.getReturnDate());
    }

    public List<CopyDto> copiesToDto(List<Copy> copies){
        return copies.stream().map(this::copyToDto).collect(Collectors.toList());
    }
}
