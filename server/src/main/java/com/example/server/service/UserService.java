package com.example.server.service;

import com.example.server.dtos.UserDto;
import com.example.server.dtos.UserProjection;
import com.example.server.exception.UserNotFoundException;
import com.example.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CopyService copyService;

    @Autowired
    public UserService(UserRepository userRepository, CopyService copyService) {
        this.userRepository = userRepository;
        this.copyService = copyService;
    }

    public Page<UserDto> getUserProjections(Pageable pageable) {
        Page<UserProjection> projections = this.userRepository.getUsers(pageable);
        if(projections.isEmpty()){
            throw new UserNotFoundException();
        }
        return projections.map(this::mapProjectionToDto);
    }


    public UserDto mapProjectionToDto(UserProjection projection){
        return new UserDto(projection, copyService.getCopiesByUserId(projection.getId()));
    }
//
    
}
