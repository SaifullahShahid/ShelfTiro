package com.example.shelftiro.controllers;

import com.example.shelftiro.domain.dto.BookRequestDto;
import com.example.shelftiro.domain.dto.BookResponseDto;
import com.example.shelftiro.domain.entities.BookEntity;
import com.example.shelftiro.mappers.BookMapper;
import com.example.shelftiro.services.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api")
public class BookController {

    private final BookService bookService;
    private final BookMapper<BookEntity, BookRequestDto, BookResponseDto> bookMapper;

    public BookController(BookService bookService, BookMapper<BookEntity, BookRequestDto,BookResponseDto> bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PostMapping(path = "/authors/{author_id}/books")   //create book by author id
    public ResponseEntity<BookResponseDto> createBook(@PathVariable("author_id") Long authorid,
                                                      @Valid @RequestBody BookRequestDto bookRequestDto){
        BookEntity bookEntity = bookMapper.mapFromBookRequestDto(bookRequestDto);
        return new ResponseEntity<>(
                bookMapper.mapToBookResponseDto(bookService.createBook(authorid,bookEntity)),
                HttpStatus.CREATED);
    }

    @GetMapping(path = "/books")    //get all books
    public Page <BookResponseDto> getBooks(@RequestParam(required = false) String isbn,
                                           @RequestParam(required = false) String title,
                                           @RequestParam(required = false) String genre,
                                           @RequestParam(required = false) String authorName,
                                           Pageable pageable){
        Page<BookEntity> books = bookService.filterBooks(isbn,title,genre,authorName,pageable);
        return books.map(bookMapper::mapToBookResponseDto);
    }

    @GetMapping(path = "/books/{book_isbn}")    //get book by book isbn
    public BookResponseDto getBookByIsbn(@PathVariable("book_isbn") String isbn){
        return bookMapper.mapToBookResponseDto(bookService.listBookByIsbn(isbn));

    }

    @GetMapping(path = "/authors/{author_id}/books")    //get all books by author id
    public Page<BookResponseDto> getBooksByAuthorId(@PathVariable("author_id")Long id, Pageable pageable){
        Page<BookEntity> books = bookService.listBooksByAuthorId(id,pageable);
        return books.map(bookMapper::mapToBookResponseDto);
    }

    @PutMapping(path = "/authors/{author_id}/books/{book_id}")  //full update book by book id
    public ResponseEntity<BookResponseDto> fullUpdateBook(@PathVariable("author_id")Long authorId,
                                                          @PathVariable("book_id")Long bookId,
                                                          @Valid @RequestBody BookRequestDto bookRequestDto){
        BookEntity bookEntity = bookMapper.mapFromBookRequestDto(bookRequestDto);
        return new ResponseEntity<>(
                bookMapper.mapToBookResponseDto(bookService.fullUpdateBook(authorId,bookId,bookEntity)),
                HttpStatus.OK);
    }
    @PatchMapping(path = "/authors/{author_id}/books/{book_id}")    //partial update book by book id
    public ResponseEntity<BookResponseDto> partialUpdateBook(@PathVariable("author_id")Long authorId,
                                                             @PathVariable("book_id")Long bookId,
                                                             @RequestBody BookRequestDto bookRequestDto){
        BookEntity bookEntity = bookMapper.mapFromBookRequestDto(bookRequestDto);
        return new ResponseEntity<>(
                bookMapper.mapToBookResponseDto(bookService.partialUpdateBook(authorId,bookId,bookEntity)),
                HttpStatus.OK);
    }
    @DeleteMapping(path = "/authors/{author_id}/books/{book_id}") //delete book by book id
    public ResponseEntity<Void> deleteBook(@PathVariable("author_id")Long authorId,
                                                      @PathVariable("book_id")Long bookId){
        bookService.deleteBook(authorId,bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
