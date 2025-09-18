package com.example.shelftiro.controllers;


import com.example.shelftiro.domain.dto.AuthorDto;
import com.example.shelftiro.domain.entities.AuthorEntity;
import com.example.shelftiro.mappers.AuthorMapper;
import com.example.shelftiro.services.AuthorService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
