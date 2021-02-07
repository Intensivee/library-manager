package com.example.server.service;

import com.example.server.dtos.UserDto;
import com.example.server.entity.User;
import com.example.server.exception.ResourceNotFoundException;
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

    public List<UserDto> getAll() {
        List<User> projections = this.userRepository.findAll();

        if(projections.isEmpty()){
            throw new ResourceNotFoundException("users");
        }
        return this.userMapper.usersToDto(projections);
    }

    public Page<UserDto> getAll(Pageable pageable) {
        Page<User> projections = this.userRepository.findAll(pageable);

        if(projections.isEmpty()){
            throw new ResourceNotFoundException("users");
        }
        return projections.map(this.userMapper::userToDto);
    }

    public UserDto getById(Long id){
        return this.userRepository.findById(id)
                .map(this.userMapper::userToDto)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", id));
    }

    public UserDto getByCopyId(Long id){
        return this.userRepository.findByCopiesId(id)
                .map(this.userMapper::userToDto)
                .orElseThrow( () -> new ResourceNotFoundException("users", "copyId", id));
    }

    public void deleteById(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", id));
        this.userRepository.delete(user);
    }

    public UserDto update(UserDto userDto){
        User userToUpdate = this.userRepository.findById(userDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", userDto.getId()));

        userToUpdate.setFirstName(userDto.getFirstName());
        userToUpdate.setLastName(userDto.getLastName());
        userToUpdate.setEmail(userDto.getEmail());
        userToUpdate.setRole(userDto.getRole());

        return this.userMapper.userToDto(this.userRepository.save(userToUpdate));
    }
}
