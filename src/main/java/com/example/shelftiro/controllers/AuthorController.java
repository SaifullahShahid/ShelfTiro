package com.example.shelftiro.controllers;

import com.example.shelftiro.domain.dto.AuthorDto;
import com.example.shelftiro.domain.entities.AuthorEntity;
import com.example.shelftiro.mappers.AuthorMapper;
import com.example.shelftiro.services.AuthorService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorMapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService,AuthorMapper<AuthorEntity,AuthorDto> authorMapper){
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@Valid @RequestBody AuthorDto authorDto){
        AuthorEntity savedAuthor = authorService.createAuthor(authorMapper.mapFromAuthorDto(authorDto));
        return new ResponseEntity<>(authorMapper.mapToAuthorDto(savedAuthor), HttpStatus.CREATED);
    }

    @GetMapping(path = "/authors")
    public List <AuthorDto> getAuthors(){
        return authorService.listAuthors().stream()
                .map(authorMapper::mapToAuthorDto)
                .toList();
    }

    @GetMapping(path = "/authors/{id}")
    public ResponseEntity <AuthorDto> getAuthorById(@PathVariable("id") Long id){
        return new ResponseEntity<>(authorMapper.mapToAuthorDto(authorService.listAuthorById(id)),HttpStatus.OK);
    }

    @PutMapping(path = "/authors/{id}")
    public ResponseEntity <AuthorDto> fullUpdateAuthor(
            @PathVariable("id") Long id,
            @Valid @RequestBody AuthorDto authorDto){

        AuthorEntity authorEntity = authorMapper.mapFromAuthorDto(authorDto);
        return new ResponseEntity<>(authorMapper.mapToAuthorDto(authorService.fullUpdateAuthor(id,authorEntity)),
                HttpStatus.OK);
    }

    @PatchMapping(path = "/authors/{id}")
    public ResponseEntity <AuthorDto> partialUpdateAuthor(
            @PathVariable("id") Long id,
            @RequestBody AuthorDto authorDto){

        AuthorEntity authorEntity = authorMapper.mapFromAuthorDto(authorDto);
        return new ResponseEntity<>(authorMapper.mapToAuthorDto(authorService.partialUpdateAuthor(id,authorEntity)),
                HttpStatus.OK);
    }

    @DeleteMapping(path="/authors/{id}")
    public void deleteAuthor(@PathVariable("id") Long id){
        authorService.deleteUser(id);
    }

}
