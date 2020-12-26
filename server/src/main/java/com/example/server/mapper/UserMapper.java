package com.example.server.mapper;

import com.example.server.dtos.UserDto;
import com.example.server.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final CopyMapper copyMapper;

    @Autowired
    public UserMapper(CopyMapper copyMapper) {
        this.copyMapper = copyMapper;
    }

    public UserDto userToDto(User user){
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole(),
                copyMapper.copiesToDto(user.getCopies())
        );
    }

    public List<UserDto> usersToDto(List<User> users){
        return users.stream().map(this::userToDto).collect(Collectors.toList());
    }
}
