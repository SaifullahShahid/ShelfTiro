package com.example.shelftiro.controllers;

import com.example.shelftiro.TestDataUtil;
import com.example.shelftiro.domain.entities.AuthorEntity;
import com.example.shelftiro.domain.entities.BookEntity;
import com.example.shelftiro.domain.entities.LoanEntity;
import com.example.shelftiro.domain.entities.UserEntity;
import com.example.shelftiro.services.AuthorService;
import com.example.shelftiro.services.BookService;
import com.example.shelftiro.services.LoanService;
import com.example.shelftiro.services.UserService;
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

import java.time.LocalDate;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc(addFilters = false)   //disables security filters
public class LoanControllerIntegrationTests {
    @Autowired
    private LoanService loanService;

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testThatCreateLoanSuccessfullyReturnsHttp201Created() throws Exception{
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.createAuthor(authorEntityA);
        BookEntity bookEntityA = TestDataUtil.createTestBookEntityA();
        bookService.createBook(authorEntityA.getId(), bookEntityA);
        UserEntity userEntityA = TestDataUtil.createTestUserEntityA();
        userService.createUser(userEntityA);
        String loanJson = "{\"book\":{\"id\":"+bookEntityA.getId()+"}}";

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/users/"+userEntityA.getId()+"/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loanJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }
    @Test
    public void testThatCreateLoanSuccessfullyReturnsSavedLoan() throws Exception{
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.createAuthor(authorEntityA);
        BookEntity bookEntityA = TestDataUtil.createTestBookEntityA();
        bookService.createBook(authorEntityA.getId(), bookEntityA);
        UserEntity userEntityA = TestDataUtil.createTestUserEntityA();
        userService.createUser(userEntityA);
        String loanJson = "{\"book\":{\"id\":"+bookEntityA.getId()+"}}";

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/users/"+userEntityA.getId()+"/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loanJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.loanDate").exists()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.dueDate").value(LocalDate.now().plusDays(14).toString())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.returnDate").isEmpty()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.user.name").value(userEntityA.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.user.email").value(userEntityA.getEmail())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.book.isbn").value(bookEntityA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.book.title").value(bookEntityA.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.book.genre").value(bookEntityA.getGenre())
        );
    }
    @Test
    public void testThatListLoansByUserIdSuccessfullyReturnsHttp200Ok() throws Exception{
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.createAuthor(authorEntityA);
        BookEntity bookEntityA = TestDataUtil.createTestBookEntityA();
        bookService.createBook(authorEntityA.getId(), bookEntityA);
        UserEntity userEntityA = TestDataUtil.createTestUserEntityA();
        userService.createUser(userEntityA);
        LoanEntity loanEntityA = TestDataUtil.createLoanEntityA();
        loanService.createLoan(userEntityA.getId(),loanEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/users/"+userEntityA.getId()+"/loans")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
    @Test
    public void testThatListLoansByUserIdSuccessfullyReturnsDesiredLoan() throws Exception{
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.createAuthor(authorEntityA);
        BookEntity bookEntityA = TestDataUtil.createTestBookEntityA();
        bookService.createBook(authorEntityA.getId(), bookEntityA);
        UserEntity userEntityA = TestDataUtil.createTestUserEntityA();
        userService.createUser(userEntityA);
        LoanEntity loanEntityA = TestDataUtil.createLoanEntityA();
        loanService.createLoan(userEntityA.getId(),loanEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/users/"+userEntityA.getId()+"/loans")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].loanDate").exists()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].dueDate").exists()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].returnDate").isEmpty()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].user.name").value(userEntityA.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].user.email").value(userEntityA.getEmail())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].book.isbn").value(bookEntityA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].book.title").value(bookEntityA.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].book.genre").value(bookEntityA.getGenre())
        );
    }
    @Test
    public void testThatReturnLoanSuccessfullyReturnsHttp200IsOk() throws Exception {
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.createAuthor(authorEntityA);
        BookEntity bookEntityA = TestDataUtil.createTestBookEntityA();
        bookService.createBook(authorEntityA.getId(), bookEntityA);
        UserEntity userEntityA = TestDataUtil.createTestUserEntityA();
        userService.createUser(userEntityA);
        LoanEntity loanEntityA = TestDataUtil.createLoanEntityA();
        loanService.createLoan(userEntityA.getId(), loanEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/loans/" + loanEntityA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
    @Test
    public void testThatReturnLoanSuccessfullyUpdatesReturnDate() throws Exception {
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.createAuthor(authorEntityA);
        BookEntity bookEntityA = TestDataUtil.createTestBookEntityA();
        bookService.createBook(authorEntityA.getId(), bookEntityA);
        UserEntity userEntityA = TestDataUtil.createTestUserEntityA();
        userService.createUser(userEntityA);
        LoanEntity loanEntityA = TestDataUtil.createLoanEntityA();
        loanService.createLoan(userEntityA.getId(), loanEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/loans/" + loanEntityA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.loanDate").exists()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.dueDate").exists()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.returnDate").isNotEmpty()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.returnDate").value(LocalDate.now().toString())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.user.name").value(userEntityA.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.user.email").value(userEntityA.getEmail())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.book.isbn").value(bookEntityA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.book.title").value(bookEntityA.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.book.genre").value(bookEntityA.getGenre())
        );
    }
    @Test
    public void testThatDeleteLoanSuccessfullyUpdatesReturnHttpNoContent() throws Exception {
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.createAuthor(authorEntityA);
        BookEntity bookEntityA = TestDataUtil.createTestBookEntityA();
        bookService.createBook(authorEntityA.getId(), bookEntityA);
        UserEntity userEntityA = TestDataUtil.createTestUserEntityA();
        userService.createUser(userEntityA);
        LoanEntity loanEntityA = TestDataUtil.createLoanEntityA();
        loanService.createLoan(userEntityA.getId(), loanEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/loans/" + loanEntityA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
    @Test
    public void testThatDeleteLoanSuccessfullyUpdatesReturnHttpNotFound404UponPassingInvalidId() throws Exception {
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.createAuthor(authorEntityA);
        BookEntity bookEntityA = TestDataUtil.createTestBookEntityA();
        bookService.createBook(authorEntityA.getId(), bookEntityA);
        UserEntity userEntityA = TestDataUtil.createTestUserEntityA();
        userService.createUser(userEntityA);
        LoanEntity loanEntityA = TestDataUtil.createLoanEntityA();
        loanService.createLoan(userEntityA.getId(), loanEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/loans/-1" )
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }


}
