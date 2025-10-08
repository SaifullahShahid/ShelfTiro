package com.example.shelftiro.controllers;

import com.example.shelftiro.domain.dto.AuthorRequestDto;
import com.example.shelftiro.domain.dto.AuthorResponseDto;
import com.example.shelftiro.domain.entities.AuthorEntity;
import com.example.shelftiro.mappers.AuthorMapper;
import com.example.shelftiro.services.AuthorService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorMapper<AuthorEntity, AuthorResponseDto, AuthorRequestDto> authorMapper;

    public AuthorController(AuthorService authorService,
                            AuthorMapper<AuthorEntity, AuthorResponseDto,AuthorRequestDto> authorMapper){
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping        //create author
    public ResponseEntity<AuthorResponseDto> createAuthor(@Valid @RequestBody AuthorRequestDto authorRequestDto){
        AuthorEntity savedAuthor = authorService.createAuthor(authorMapper.mapFromAuthorRequestDto(authorRequestDto));
        return new ResponseEntity<>(authorMapper.mapToAuthorResponseDto(savedAuthor), HttpStatus.CREATED);
    }

    @GetMapping         //get all authors
    public List <AuthorResponseDto> getAuthors(){
        return authorService.listAuthors().stream()
                .map(authorMapper::mapToAuthorResponseDto)
                .toList();
    }

    @GetMapping(path = "/{id}")         //get author by author id
    public ResponseEntity <AuthorResponseDto> getAuthorById(@PathVariable("id") Long id){
        return new ResponseEntity<>(authorMapper.mapToAuthorResponseDto(authorService.listAuthorById(id)),HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")         //full update author by author id
    public ResponseEntity <AuthorResponseDto> fullUpdateAuthor(
            @PathVariable("id") Long id,
            @Valid @RequestBody AuthorRequestDto authorRequestDto){

        AuthorEntity authorEntity = authorMapper.mapFromAuthorRequestDto(authorRequestDto);
        return new ResponseEntity<>(authorMapper.mapToAuthorResponseDto(authorService.fullUpdateAuthor(id,authorEntity)),
                HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}")       //partial update author by author id
    public ResponseEntity <AuthorResponseDto> partialUpdateAuthor(
            @PathVariable("id") Long id,
            @RequestBody AuthorRequestDto authorRequestDto){

        AuthorEntity authorEntity = authorMapper.mapFromAuthorRequestDto(authorRequestDto);
        return new ResponseEntity<>(authorMapper.mapToAuthorResponseDto(authorService.partialUpdateAuthor(id,authorEntity)),
                HttpStatus.OK);
    }

    @DeleteMapping(path="/{id}")        //delte author by author id
    public void deleteAuthor(@PathVariable("id") Long id){
        authorService.deleteAuthor(id);
    }

}
