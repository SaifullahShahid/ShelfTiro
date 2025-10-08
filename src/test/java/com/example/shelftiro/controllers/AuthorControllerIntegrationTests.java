package com.example.shelftiro.controllers;

import com.example.shelftiro.TestDataUtil;
import com.example.shelftiro.domain.entities.AuthorEntity;
import com.example.shelftiro.domain.entities.BookEntity;
import com.example.shelftiro.repositories.BookRepository;
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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc(addFilters = false)   //disables security filters
public class AuthorControllerIntegrationTests {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception{
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        String authorJson = objectMapper.writeValueAsString(authorEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }
    @Test
    public void testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception{
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        String authorJson = objectMapper.writeValueAsString(authorEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("J.K. Rowling")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.birthDate").value("1965-07-31")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.countryOrigin").value("United Kingdom")
        );
    }
    @Test
    public void testThatCreateAuthorSuccessfullyReturnsHttp400BadRequestUponPassingInvalidAuthorName() throws Exception{
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorEntityA.setName("");
        String blankAuthorJson = objectMapper.writeValueAsString(authorEntityA);
        authorEntityA.setName(null);
        String nullAuthorJson = objectMapper.writeValueAsString(authorEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(blankAuthorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(nullAuthorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }
    @Test
    public void testThatCreateAuthorSuccessfullyReturnsHttp400BadRequestUponPassingUnknownAuthor() throws Exception{
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorEntityA.setName("Unknown Author");
        authorService.createAuthor(authorEntityA);
        authorEntityA.setName("Unknown Author");
        String invalidAuthorJson = objectMapper.writeValueAsString(authorEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidAuthorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }
    @Test
    public void testThatListAuthorsSuccessfullyReturnsHttp200() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
    @Test
    public void testThatListAuthorByIdSuccessfullyReturnsDesiredAuthor() throws Exception{
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.createAuthor(authorEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/authors/"+authorEntityA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("J.K. Rowling")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.birthDate").value("1965-07-31")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.countryOrigin").value("United Kingdom")
        );
    }
    @Test
    public void testThatFullUpdateAuthorSuccessfullyReturnsHttp200Ok() throws Exception{
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.createAuthor(authorEntityA);
        authorEntityA.setName("TestName");
        authorEntityA.setBirthDate(LocalDate.parse("2025-09-15"));
        authorEntityA.setCountryOrigin("TestCountry");

        String authorJson = objectMapper.writeValueAsString(authorEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/authors/"+authorEntityA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
    @Test
    public void testThatFullUpdateAuthorSuccessfullyReturnsUpdatedAuthor() throws Exception{
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.createAuthor(authorEntityA);
        authorEntityA.setName("TestName");
        authorEntityA.setBirthDate(LocalDate.parse("2025-09-15"));
        authorEntityA.setCountryOrigin("TestCountry");

        String authorJson = objectMapper.writeValueAsString(authorEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/authors/"+authorEntityA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("TestName")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.birthDate").value("2025-09-15")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.countryOrigin").value("TestCountry")
        );
    }
    @Test
    public void testThatpartialUpdateAuthorSuccessfullyReturnsUpdatedAuthor() throws Exception{
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.createAuthor(authorEntityA);
        authorEntityA.setName("TestName");
        authorEntityA.setBirthDate(LocalDate.parse("2025-09-15"));

        String authorJson = objectMapper.writeValueAsString(authorEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/authors/"+authorEntityA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("TestName")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.birthDate").value("2025-09-15")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.countryOrigin").value(authorEntityA.getCountryOrigin())
        );
    }
    @Test
    public void testThatDeleteAuthorSuccessfullyReturnsHttp200IsOK() throws Exception{
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.createAuthor(authorEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/authors/"+authorEntityA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
    @Test
    public void testThatDeleteAuthorSuccessfullyReturnsHttp404NotFoundUponInvalidId() throws Exception{
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.createAuthor(authorEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/authors/-1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }
    @Test
    public void testThatDeleteAuthorSuccessfullyUpdatesBookAuthorId() throws Exception{
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.createAuthor(authorEntityA);
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA();
        BookEntity savedbook = bookService.createBook(authorEntityA.getId(),bookEntity);
        AuthorEntity unknownAuthor = AuthorEntity.builder()
                                    .name("Unknown Author") //Pre Registered by the name "Unknown Author" in db
                                    .build();
        AuthorEntity savedUnknown = authorService.createAuthor(unknownAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/authors/"+authorEntityA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
        BookEntity updatedBook = bookRepository.findById(savedbook.getId()).get();
        assertEquals(savedUnknown.getId(), updatedBook.getAuthorEntity().getId());
    }

}
