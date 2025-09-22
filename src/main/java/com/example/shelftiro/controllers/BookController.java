package com.example.shelftiro.controllers;

import com.example.shelftiro.domain.dto.BookRequestDto;
import com.example.shelftiro.domain.dto.BookResponseDto;
import com.example.shelftiro.domain.entities.BookEntity;
import com.example.shelftiro.mappers.BookMapper;
import com.example.shelftiro.services.impl.BookServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(path="/api")
public class BookController {

    private final BookServiceImpl bookService;
    private final BookMapper<BookEntity, BookRequestDto, BookResponseDto> bookMapper;

    public BookController(BookServiceImpl bookService, BookMapper<BookEntity, BookRequestDto,BookResponseDto> bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PostMapping(path = "/authors/{author_id}/books")
    public ResponseEntity<BookResponseDto> createBook(@PathVariable("author_id") Long id,
                                                      @Valid @RequestBody BookRequestDto bookRequestDto){
        BookEntity bookEntity = bookMapper.mapFromBookRequestDto(bookRequestDto);
        return new ResponseEntity<>(
                bookMapper.mapToBookResponseDto(bookService.createBook(id,bookEntity)),
                HttpStatus.CREATED);
    }

    @GetMapping(path = "/books")
    public Page <BookResponseDto> getBooks(@RequestParam(required = false) String isbn,
                                           @RequestParam(required = false) String title,
                                           @RequestParam(required = false) String genre,
                                           @RequestParam(required = false) String authorName,
                                           Pageable pageable){
        Page<BookEntity> books = bookService.filterBooks(isbn,title,genre,authorName,pageable);
        return books.map(bookMapper::mapToBookResponseDto);
    }

    @GetMapping(path = "/books/{book_isbn}")
    public BookResponseDto getBookByIsbn(@PathVariable("book_isbn") String isbn){
        return bookMapper.mapToBookResponseDto(bookService.listBookByIsbn(isbn));

    }

    @GetMapping(path = "/authors/{author_id}/books")
    public Page<BookResponseDto> getBooksByAuthorId(@PathVariable("author_id")Long id, Pageable pageable){
        Page<BookEntity> books = bookService.listBooksByAuthorId(id,pageable);
        return books.map(bookMapper::mapToBookResponseDto);
    }

    @PatchMapping(path = "/authors/{author_id}/books")
    public ResponseEntity<BookResponseDto> fullUpdateBook(@PathVariable("author_id")Long id,
                                                          @Valid @RequestBody BookRequestDto bookRequestDto){
        BookEntity bookEntity = bookMapper.mapFromBookRequestDto(bookRequestDto);
        return new ResponseEntity<>(
                bookMapper.mapToBookResponseDto(bookService.fullUpdateBook(id,bookEntity)),
                HttpStatus.OK);
    }



}
