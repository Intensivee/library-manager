package com.example.server.service;

import com.example.server.dtos.UserDto;
import com.example.server.dtos.UserProjection;
import com.example.server.entity.User;
import com.example.server.exception.UserNotFoundException;
import com.example.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CopyService copyService;

    @Autowired
    public UserService(UserRepository userRepository, CopyService copyService) {
        this.userRepository = userRepository;
        this.copyService = copyService;
    }

    public Page<UserDto> getUserProjectionsPaginated(Pageable pageable) {
        Page<UserProjection> projections = this.userRepository.getUsersPaginated(pageable);
        if(projections.isEmpty()){
            throw new UserNotFoundException();
        }
        return projections.map(this::mapProjectionToDto);
    }

    public List<UserDto> getUsersProjections() {
        List<UserProjection> projections = this.userRepository.getUsers();
        if(projections.isEmpty()){
            throw new UserNotFoundException();
        }
        return projections.stream().map(this::mapProjectionToDto).collect(Collectors.toList());
    }

    public UserDto getUserDtoById(Long id){
        UserProjection projection = this.userRepository.getUserById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return this.mapProjectionToDto(projection);
    }

    public UserDto updateUser(UserDto userDto){
        User userToUpdate = this.userRepository.getOne(userDto.getId());
        userToUpdate.setFirstName(userDto.getFirstName());
        userToUpdate.setLastName(userDto.getLastName());
        userToUpdate.setUsername(userDto.getUsername());
        userToUpdate.setEmail(userDto.getEmail());
        userToUpdate.setRole(userDto.getRole());
        return this.mapModelToDto(this.userRepository.save(userToUpdate));
    }

    public UserDto mapProjectionToDto(UserProjection projection){
        return new UserDto(projection, copyService.getCopiesByUserId(projection.getId()));
    }

    public UserDto mapModelToDto(User user){
        return new UserDto(user, copyService.getCopiesByUserId(user.getId()));
    }

}
