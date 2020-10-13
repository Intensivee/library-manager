package com.example.server.service;

import com.example.server.dtos.CopyDto;
import com.example.server.dtos.CopyProjection;
import com.example.server.repository.CopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CopyService {

    private CopyRepository copyRepository;

    @Autowired
    public CopyService(CopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }

    public List<CopyDto> getCopiesByUserId(Long id){
        List<CopyProjection> projections = this.copyRepository.findByUserId(id);
        return projections.stream().map(this::mapProjectionToDto).collect(Collectors.toList());
    }

    public CopyDto mapProjectionToDto(CopyProjection projection){
        return new CopyDto(projection);
    }
}
