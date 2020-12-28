package com.example.server.controller;

import com.example.server.dtos.CopyDto;
import com.example.server.entity.Copy;
import com.example.server.payload.PostResponse;
import com.example.server.service.CopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("copies")
public class CopyController {

    private final CopyService copyService;

    @Autowired
    public CopyController(CopyService copyService) {
        this.copyService = copyService;
    }

    @PostMapping
    public ResponseEntity<PostResponse> createCopy(@RequestBody CopyDto copyDto){
        Copy copy = this.copyService.createCopy(copyDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(copy.getId())
                .toUri();

        return ResponseEntity.created(location).body(new PostResponse(copy.getId(), location));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCopy(@PathVariable("id") Long id){
        this.copyService.deleteCopy(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/search/findBorrowed")
    public ResponseEntity<Object> getBorrowedCopies(){
        List<EntityModel<?>> copies = this.copyService.getBorrowedCopies().stream()
                .map(copy -> EntityModel.of(copy, linkTo(CopyController.class).slash(copy.getId()).withSelfRel()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(new CollectionModel<>(copies).add(linkTo(CopyController.class).withSelfRel()));
    }

    @GetMapping("/search/findByBookId/{id}")
    public ResponseEntity<Object> getAllByBookId(@PathVariable("id") Long id){
        List<EntityModel<?>> copies = this.copyService.getAllByBookId(id).stream()
                .map(copy -> EntityModel.of(copy, linkTo(CopyController.class).slash(id).withSelfRel()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(new CollectionModel<>(copies).add(linkTo(CopyController.class).withSelfRel()));
    }

    @GetMapping("/search/findByUserId/{id}")
    public ResponseEntity<Object> getAllByUserId(@PathVariable("id") Long id){
        List<EntityModel<?>> copies = this.copyService.getAllByUserId(id).stream()
                .map(copy -> EntityModel.of(copy, linkTo(CopyController.class).slash(id).withSelfRel()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(new CollectionModel<>(copies).add(linkTo(CopyController.class).withSelfRel()));
    }
}
