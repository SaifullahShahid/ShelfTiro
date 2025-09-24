package com.example.shelftiro.services.impl;

import com.example.shelftiro.domain.entities.AuthorEntity;
import com.example.shelftiro.domain.entities.BookEntity;
import com.example.shelftiro.repositories.AuthorRepository;
import com.example.shelftiro.repositories.BookRepository;
import com.example.shelftiro.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;


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
    public Page<BookEntity> listBooksByAuthorId(Long id, Pageable pageable) {
        if(!authorRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author does not exist by this id!");
        }
        return bookRepository.findByAuthorEntity_Id(id,pageable);

    }

    @Override
    public BookEntity listBookByIsbn(String isbn) {
            return bookRepository.getByIsbn(isbn).orElseThrow(()->
                    new ResponseStatusException(HttpStatus.NOT_FOUND,"Book by this ISBN does not exist!"));
    }

    @Override
    public Page<BookEntity> filterBooks(String isbn, String title, String genre, String authorName, Pageable pageable) {

        if(isbn!=null){
            return bookRepository.findByIsbn(isbn,pageable);
        }
        if(genre!=null&&authorName!=null){
            return bookRepository.findByGenreIgnoreCaseAndAuthorEntity_NameIgnoreCase(genre,authorName,pageable);
        }
        if(title!=null&&authorName!=null){
            return bookRepository.findByTitleIgnoreCaseOrAuthorEntity_NameIgnoreCase(title,authorName,pageable);
        }
        if(title!=null&&genre!=null){
            return bookRepository.findByTitleIgnoreCaseAndGenreIgnoreCase(title,genre,pageable);
        }
        if(title!=null){
            return bookRepository.findByTitleIgnoreCase(title,pageable);
        }
        if(genre!=null){
            return bookRepository.findByGenreIgnoreCase(genre,pageable);
        }
        if(authorName!=null){
            return bookRepository.findByAuthorEntity_NameIgnoreCase(authorName,pageable);
        }
        return bookRepository.findAll(pageable);
    }

    @Override
    public BookEntity fullUpdateBook(Long authorId, Long bookId, BookEntity bookEntity) {
        AuthorEntity author = authorRepository.findById(authorId).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Author does not exist by this id!"));
        return bookRepository.findById(bookId).map(existingBook -> {
            if (!existingBook.getAuthorEntity().getId().equals(authorId)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "This book does not belong to the given author");
            }
            Optional.of(bookEntity.getIsbn()).ifPresent(existingBook::setIsbn);
            Optional.of(bookEntity.getTitle()).ifPresent(existingBook::setTitle);
            Optional.of(bookEntity.getGenre()).ifPresent(existingBook::setGenre);
            existingBook.setAuthorEntity(author);
            return bookRepository.save(existingBook);
        }).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"Book by this Id does not exist!"));
    }
    @Override
    public BookEntity partialUpdateBook(Long authorId, Long bookId, BookEntity bookEntity) {
        AuthorEntity author = authorRepository.findById(authorId).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Author does not exist by this id!"));
        return bookRepository.findById(bookId).map(existingBook -> {
            if (!existingBook.getAuthorEntity().getId().equals(authorId)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "This book does not belong to the given author");
            }
            Optional.ofNullable(bookEntity.getIsbn()).ifPresent(existingBook::setIsbn);
            Optional.ofNullable(bookEntity.getTitle()).ifPresent(existingBook::setTitle);
            Optional.ofNullable(bookEntity.getGenre()).ifPresent(existingBook::setGenre);
            existingBook.setAuthorEntity(author);
            return bookRepository.save(existingBook);
        }).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"Book by this Id does not exist!"));
    }

    @Override
    public void deleteBook(Long authorId, Long bookId) {
        if(!authorRepository.existsById(authorId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Author by this Id does not exist!");
        }

        BookEntity book = bookRepository.findById(bookId).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"Book by this Id does not exist!"));

        if (!book.getAuthorEntity().getId().equals(authorId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "This book does not belong to the given author");
        }
        bookRepository.delete(book);
    }
}
