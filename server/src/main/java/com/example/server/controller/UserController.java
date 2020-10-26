package com.example.server.controller;

import com.example.server.dtos.UserDto;
import com.example.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<?>> getById(@PathVariable("id") Long id) {
        logger.info("get");
        UserDto user = this.userService.getById(id);
        EntityModel<?> entityModel = EntityModel.of(user, linkTo(UserController.class).slash(id).withSelfRel());
        return ResponseEntity.ok(entityModel);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody UserDto user){
        logger.info("put");
        UserDto userUpdated = this.userService.updateUser(user);
        return ResponseEntity.ok(userUpdated);
    }

    @GetMapping("/paged")
    public ResponseEntity<?> getAll(Pageable pageable, PagedResourcesAssembler<UserDto> assembler){
        Page<UserDto> page = this.userService.getAll(pageable);
        return ResponseEntity.ok(assembler.toModel(page));
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<EntityModel<?>> users =  this.userService.getAll().stream()
                .map(user -> EntityModel.of(user, linkTo(BookController.class).slash(user.getId()).withSelfRel()))
                .collect(Collectors.toList());

        // wrapping to collection with href link
        CollectionModel<EntityModel<?>> collection = new CollectionModel<>(users).add(linkTo(UserController.class).withSelfRel());
        return ResponseEntity.ok(collection);
    }
}
