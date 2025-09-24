package com.example.shelftiro.services.impl;

import com.example.shelftiro.domain.entities.AuthorEntity;
import com.example.shelftiro.domain.entities.BookEntity;
import com.example.shelftiro.repositories.AuthorRepository;
import com.example.shelftiro.repositories.BookRepository;
import com.example.shelftiro.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository,BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {
        if ((authorEntity.getName().equalsIgnoreCase("UNKNOWN AUTHOR"))
                && authorRepository.existsByNameIgnoreCase("UNKNOWN AUTHOR")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "UNKNOWN AUTHOR already exists");
        }
        return authorRepository.save(authorEntity);
    }

    @Override
    public List<AuthorEntity> listAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public AuthorEntity listAuthorById(Long id) {
        Optional <AuthorEntity> existingAuthor = authorRepository.findById(id);

        if (existingAuthor.isPresent()) {
            return existingAuthor.get();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author does not exist by this id!");
        }

    }
    @Override
    public AuthorEntity fullUpdateAuthor(Long id, AuthorEntity autherEntity) {
        return authorRepository.findById(id).map(existingAuthor -> {
            existingAuthor.setName(autherEntity.getName());
            existingAuthor.setBirthDate(autherEntity.getBirthDate());
            existingAuthor.setCountryOrigin(autherEntity.getCountryOrigin());
            return authorRepository.save(existingAuthor);
        }).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Author does not exist by this id!"));
    }

    @Override
    public AuthorEntity partialUpdateAuthor(Long id, AuthorEntity authorEntity) {
        return  authorRepository.findById(id).map(existingAuthor -> {
            Optional.ofNullable(authorEntity.getName()).ifPresent(existingAuthor::setName);
            Optional.ofNullable(authorEntity.getBirthDate()).ifPresent(existingAuthor::setBirthDate);
            Optional.ofNullable(authorEntity.getCountryOrigin()).ifPresent(existingAuthor::setCountryOrigin);
            return authorRepository.save(existingAuthor);
        }).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Author does not exist by this id!"));
    }

    @Override
    public void deleteAuthor(Long id) {
        AuthorEntity unknownAuthor = authorRepository.findByNameIgnoreCase("unknown Author");

        if (authorRepository.existsById(id)){
            List<BookEntity> books = bookRepository.findByAuthorEntity_Id(id);

            for (BookEntity book : books) {
                book.setAuthorEntity(unknownAuthor);
                bookRepository.save(book);
            }

            authorRepository.deleteById(id);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author by this id does not exist!");
        }
    }

}
