package com.example.shelftiro.controllers;

import com.example.shelftiro.TestDataUtil;
import com.example.shelftiro.domain.entities.AuthorEntity;
import com.example.shelftiro.services.AuthorService;
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
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception{
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        String userJson = objectMapper.writeValueAsString(authorEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
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
}
