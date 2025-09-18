package com.example.shelftiro.services;

import com.example.shelftiro.domain.entities.AuthorEntity;

import java.util.List;

public interface AuthorService {
    AuthorEntity createAuthor(AuthorEntity authorEntity);

    List <AuthorEntity> listAuthors();

    AuthorEntity listAuthorById(Long id);

    AuthorEntity fullUpdateAuthor(Long id, AuthorEntity autherEntity);

    AuthorEntity partialUpdateAuthor(Long id, AuthorEntity authorEntity);
}
