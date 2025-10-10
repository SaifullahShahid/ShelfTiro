package com.example.shelftiro.services;

import com.example.shelftiro.domain.entities.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;

import java.util.List;

public interface BookService {
    BookEntity createBook(Long authorid, BookEntity bookEntity);

    List<BookEntity> listBooks();

    Page<BookEntity> listBooksByAuthorId(Long id, Pageable pageable);

    BookEntity listBookByIsbn(String isbn);

    Page<BookEntity> filterBooks(String isbn, String title, String genre, String authorName, Pageable pageable);

    BookEntity fullUpdateBook(Long authorId, Long bookId, BookEntity bookEntity);

    BookEntity partialUpdateBook(Long authorId, Long bookId, BookEntity bookEntity);

    void deleteBook(Long authorId, Long bookId);
}
