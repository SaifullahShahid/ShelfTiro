package com.example.shelftiro.services.impl;

import com.example.shelftiro.domain.entities.AuthorEntity;
import com.example.shelftiro.domain.entities.BookEntity;
import com.example.shelftiro.repositories.AuthorRepository;
import com.example.shelftiro.repositories.BookRepository;
import com.example.shelftiro.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }


    @Override
    public BookEntity createBook(Long id, BookEntity bookEntity) {
        AuthorEntity author = authorRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Author does not exist by this id!"));
        bookEntity.setAuthorEntity(author);
        return bookRepository.save(bookEntity);
    }

    @Override
    public List<BookEntity> listBooks() {
        return bookRepository.findAll();
    }

    @Override
    public BookEntity listBookByIsbn(String isbn) {
            return bookRepository.findByIsbn(isbn).orElseThrow(()->
                    new ResponseStatusException(HttpStatus.NOT_FOUND,"Book by this ISBN does not exist!"));
    }
}
