package com.example.server.service;

import com.example.server.dtos.UserDto;
import com.example.server.entity.User;
import com.example.server.exception.UserNotFoundException;
import com.example.server.mapper.UserMapper;
import com.example.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Page<UserDto> getUsers(Pageable pageable) {
        Page<User> projections = this.userRepository.findAll(pageable);
        if(projections.isEmpty()){
            throw new UserNotFoundException();
        }
        return projections.map(this.userMapper::userToDto);
    }

    public List<UserDto> getUsers() {
        List<User> projections = this.userRepository.findAll();
        if(projections.isEmpty()){
            throw new UserNotFoundException();
        }
        return this.userMapper.usersToDto(projections);
    }

    public UserDto getUserById(Long id){
        User user = this.userRepository.getUserById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return this.userMapper.userToDto(user);
    }

    public UserDto updateUser(UserDto userDto){
        User userToUpdate = this.userRepository.getUserById(userDto.getId())
                .orElseThrow(() -> new UserNotFoundException(userDto.getId()));
        userToUpdate.setFirstName(userDto.getFirstName());
        userToUpdate.setLastName(userDto.getLastName());
        userToUpdate.setUsername(userDto.getUsername());
        userToUpdate.setEmail(userDto.getEmail());
        userToUpdate.setRole(userDto.getRole());
        return this.userMapper.userToDto(this.userRepository.save(userToUpdate));
    }


}
