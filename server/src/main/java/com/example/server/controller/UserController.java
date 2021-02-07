package com.example.server.controller;

import com.example.server.dtos.UserDto;
import com.example.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Object> getAll(){
        List<EntityModel<?>> users =  this.userService.getAll().stream()
                .map(user -> EntityModel.of(user, linkTo(BookController.class).slash(user.getId()).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<?>> collection = new CollectionModel<>(users).add(linkTo(UserController.class).withSelfRel());
        return ResponseEntity.ok(collection);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        UserDto user = this.userService.getById(id);
        EntityModel<?> entityModel = EntityModel.of(user, linkTo(UserController.class).slash(id).withSelfRel());
        return ResponseEntity.ok(entityModel);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateById(@PathVariable("id") Long id, @RequestBody UserDto user){
        UserDto userUpdated = this.userService.updateUser(user);
        return ResponseEntity.ok(userUpdated);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id){
        this.userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/paged")
    public ResponseEntity<Object> getAllPaged(Pageable pageable, PagedResourcesAssembler<UserDto> assembler){
        Page<UserDto> page = this.userService.getAll(pageable);
        return ResponseEntity.ok(assembler.toModel(page));
    }

    @GetMapping("/search/findByCopyId/{id}")
    public ResponseEntity<Object> getByCopyId(@PathVariable("id") Long id){
        UserDto user = this.userService.getByCopyId(id);
        return ResponseEntity.ok(EntityModel.of(user, linkTo(UserController.class).slash(user.getId()).withSelfRel()));
    }
}
