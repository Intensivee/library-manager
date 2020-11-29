package com.example.server.service;

import com.example.server.dtos.UserDto;
import com.example.server.entity.User;
import com.example.server.exception.UserNotFoundException;
import com.example.server.exception.UserOwnsCopiesDeleteException;
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
            throw new UserNotFoundException();
        }
        return this.userMapper.usersToDto(projections);
    }

    public Page<UserDto> getAll(Pageable pageable) {
        Page<User> projections = this.userRepository.findAll(pageable);
        if(projections.isEmpty()){
            throw new UserNotFoundException();
        }
        return projections.map(this.userMapper::userToDto);
    }

    public UserDto getById(Long id){
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return this.userMapper.userToDto(user);
    }

    public UserDto getByCopyId(Long id){
        User user = this.userRepository.findByCopiesId(id)
                .orElseThrow(UserNotFoundException::new);
        return this.userMapper.userToDto(user);
    }

    public UserDto updateUser(UserDto userDto){
        User userToUpdate = this.userRepository.findById(userDto.getId())
                .orElseThrow(() -> new UserNotFoundException(userDto.getId()));
        userToUpdate.setFirstName(userDto.getFirstName());
        userToUpdate.setLastName(userDto.getLastName());
        userToUpdate.setUsername(userDto.getUsername());
        userToUpdate.setEmail(userDto.getEmail());
        userToUpdate.setRole(userDto.getRole());
        return this.userMapper.userToDto(this.userRepository.save(userToUpdate));
    }


    public void deleteUser(Long id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        if(!user.getCopies().isEmpty()){
            throw new UserOwnsCopiesDeleteException(id);
        }
        this.userRepository.delete(user);
    }
}
