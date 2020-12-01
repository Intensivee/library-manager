package com.example.server.service;

import com.example.server.dtos.CopyDto;
import com.example.server.entity.Copy;
import com.example.server.exception.CopyNotFoundException;
import com.example.server.exception.ObjectCreateException;
import com.example.server.exception.UserOwnsCopiesDeleteException;
import com.example.server.mapper.CopyMapper;
import com.example.server.repository.CopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CopyService {

    private final CopyRepository copyRepository;
    private final CopyMapper copyMapper;

    @Autowired
    public CopyService(CopyRepository copyRepository, CopyMapper copyMapper) {
        this.copyRepository = copyRepository;
        this.copyMapper = copyMapper;
    }

    public List<CopyDto> getAllByBookId(Long id){
        List<Copy> copies = this.copyRepository.findByBookId(id);
        return this.copyMapper.copiesToDto(copies);
    }

    public List<CopyDto> getAllByUserId(Long id){
        List<Copy> copies = this.copyRepository.findByUserId(id);
        return this.copyMapper.copiesToDto(copies);
    }

    public List<CopyDto> getBorrowedCopies(){
        List<Copy> copies = copyRepository.findByBorrowedTrue();
        return this.copyMapper.copiesToDto(copies);
    }

    public Copy createCopy(CopyDto dtoCopy) {
        if(dtoCopy.getId() != null){
            throw new ObjectCreateException(dtoCopy.getId());
        }
        else if(dtoCopy.getBookId() == null || dtoCopy.getPages() == null || dtoCopy.getBorrowed() == null){
            throw new ObjectCreateException();
        }
        return this.copyRepository.save(this.copyMapper.dtoToCopy(dtoCopy));
    }

    public void deleteCopy(Long id) {
        Copy copy = this.copyRepository.findById(id).orElseThrow( () -> new CopyNotFoundException(id));
        if(copy.getUser() != null){
            throw new UserOwnsCopiesDeleteException(copy.getUser().getId());
        }
        this.copyRepository.delete(copy);
    }
}
