package com.example.shelftiro.services;

import com.example.shelftiro.domain.entities.BookEntity;

import java.util.List;

public interface BookService {
    BookEntity createBook(Long id, BookEntity bookEntity);

    List<BookEntity> listBooks();

    BookEntity listBookByIsbn(String isbn);
}
