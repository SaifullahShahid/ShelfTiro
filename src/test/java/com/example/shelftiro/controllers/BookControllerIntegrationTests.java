package com.example.shelftiro.controllers;

import com.example.shelftiro.TestDataUtil;
import com.example.shelftiro.domain.entities.AuthorEntity;
import com.example.shelftiro.domain.entities.BookEntity;
import com.example.shelftiro.services.AuthorService;
import com.example.shelftiro.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc(addFilters = false)   //disables security filters
public class BookControllerIntegrationTests {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testThatCreateBookSuccessfullyReturnsHttp201Created() throws Exception{
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.createAuthor(authorEntityA);
        BookEntity bookEntityA = TestDataUtil.createTestBookEntityA();
        String bookJson = objectMapper.writeValueAsString(bookEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/authors/"+authorEntityA.getId()+"/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }
    @Test
    public void testThatCreateBookSuccessfullyReturnsSavedBook() throws Exception{
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.createAuthor(authorEntityA);
        BookEntity bookEntityA = TestDataUtil.createTestBookEntityA();
        String bookJson = objectMapper.writeValueAsString(bookEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/authors/"+authorEntityA.getId()+"/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("11111")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Harry Potter And The Philosopher's Stone")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.genre").value("Fantasy Fiction")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author.name").value("J.K. Rowling")
        );
    }
    @Test
    public void testThatListBooksSuccessfullyReturnsHttp200Ok() throws Exception{
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.createAuthor(authorEntityA);
        BookEntity bookEntityA = TestDataUtil.createTestBookEntityA();
        bookService.createBook(authorEntityA.getId(),bookEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
    @Test
    public void testThatListBookByIsbnSuccessfullyReturnsDesiredBook() throws Exception{
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.createAuthor(authorEntityA);
        BookEntity bookEntityA = TestDataUtil.createTestBookEntityA();
        bookService.createBook(authorEntityA.getId(),bookEntityA);


        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/books/"+bookEntityA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("11111")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Harry Potter And The Philosopher's Stone")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.genre").value("Fantasy Fiction")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author.name").value("J.K. Rowling")
        );
    }
    @Test
    public void testThatListBookByIdSuccessfullyReturnsHttp404NotFoundUponPassingInvalidIsbn() throws Exception{
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.createAuthor(authorEntityA);
        BookEntity bookEntityA = TestDataUtil.createTestBookEntityA();
        bookService.createBook(authorEntityA.getId(),bookEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/books/-1")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }
    @Test
    public void testThatfullUpdateBookSuccessfullyReturnsHttp200IsOk() throws Exception{
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.createAuthor(authorEntityA);
        BookEntity bookEntityA = TestDataUtil.createTestBookEntityA();
        bookService.createBook(authorEntityA.getId(),bookEntityA);
        BookEntity updatedBook = new BookEntity();
        updatedBook.setIsbn("Test isbn");
        updatedBook.setTitle("Test title");
        updatedBook.setGenre("Test genre");
        String bookJson = objectMapper.writeValueAsString(updatedBook);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/authors/"+authorEntityA.getId()+"/books/"+bookEntityA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
    @Test
    public void testThatfullUpdateBookSuccessfullyReturnsUpdatedBook() throws Exception{
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.createAuthor(authorEntityA);
        BookEntity bookEntityA = TestDataUtil.createTestBookEntityA();
        bookService.createBook(authorEntityA.getId(),bookEntityA);
        BookEntity updatedBook = new BookEntity();
        updatedBook.setIsbn("Test isbn");
        updatedBook.setTitle("Test title");
        updatedBook.setGenre("Test genre");
        String bookJson = objectMapper.writeValueAsString(updatedBook);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/authors/"+authorEntityA.getId()+"/books/"+bookEntityA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("Test isbn")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Test title")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.genre").value("Test genre")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author.name").value("J.K. Rowling")
        );
    }
    @Test
    public void testThatPartialUpdateBookSuccessfullyReturnsUpdatedBook() throws Exception{
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.createAuthor(authorEntityA);
        BookEntity bookEntityA = TestDataUtil.createTestBookEntityA();
        bookService.createBook(authorEntityA.getId(),bookEntityA);
        BookEntity updatedBook = new BookEntity();
        updatedBook.setIsbn("Test isbn");
        updatedBook.setGenre("Test genre");
        String bookJson = objectMapper.writeValueAsString(updatedBook);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/authors/"+authorEntityA.getId()+"/books/"+bookEntityA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("Test isbn")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Harry Potter And The Philosopher's Stone")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.genre").value("Test genre")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author.name").value("J.K. Rowling")
        );
    }
    @Test
    public void testThatDeleteBookSuccessfullyReturnsHttp204NoContent() throws Exception{
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.createAuthor(authorEntityA);
        BookEntity book = TestDataUtil.createTestBookEntityA();
        bookService.createBook(authorEntityA.getId(),book);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/authors/"+authorEntityA.getId()+"/books/"+book.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }


}
