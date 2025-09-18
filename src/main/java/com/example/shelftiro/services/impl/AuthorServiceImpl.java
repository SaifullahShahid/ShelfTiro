package com.example.shelftiro.services.impl;

import com.example.shelftiro.domain.entities.AuthorEntity;
import com.example.shelftiro.repositories.AuthorRepository;
import com.example.shelftiro.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {
        if ((authorEntity.getName().equalsIgnoreCase("UNKNOWN AUTHOR"))
                && authorRepository.existsByNameIgnoreCase("UNKNOWN AUTHOR")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "UNKNOWN AUTHOR already exists");
        }
        return authorRepository.save(authorEntity);
    }
}
