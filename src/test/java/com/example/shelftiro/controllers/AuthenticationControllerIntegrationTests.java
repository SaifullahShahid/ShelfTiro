package com.example.shelftiro.controllers;

import com.example.shelftiro.TestDataUtil;
import com.example.shelftiro.domain.dto.UserDto;
import com.example.shelftiro.domain.entities.UserEntity;
import com.example.shelftiro.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthenticationControllerIntegrationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Test
    public void testThatCreateUserSuccessfullyReturnsHttp201Created() throws Exception{
        UserDto userDto = TestDataUtil.createTestUserDtoA();
        String userJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );

    }
        @Test
    public void testThatCreateUserSuccessfullyReturnsSavedUser() throws Exception {

        UserDto userDto = TestDataUtil.createTestUserDtoB();
        String userJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Devtiro")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value("devtiro@gmail.com")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(30)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.role").value("ROLE_ADMIN")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.createdDate").exists()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").exists()
        );
    }
    @Test
    public void testLoginSuccessfullyReturnsJwtToken() throws Exception {
        UserEntity userEntity = TestDataUtil.createTestUserEntityA();
        String loginJson = objectMapper.writeValueAsString(userEntity);
        userService.createUser(userEntity);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(loginJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.token").exists()
        );
    }
    @Test
    public void testLoginSuccessfullyReturns401IsUnAuthorizedUponInvalidPassword() throws Exception {
        UserEntity userEntity = TestDataUtil.createTestUserEntityA();
        userService.createUser(userEntity);
        userEntity.setPassword("testInvalid");
        String loginJson = objectMapper.writeValueAsString(userEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson)
        ).andExpect(
                MockMvcResultMatchers.status().isUnauthorized()
        );
    }
    @Test
    public void testLoginSuccessfullyReturns401IsUnAuthorizedUponInvalidUsername() throws Exception {
        UserEntity userEntity = TestDataUtil.createTestUserEntityA();
        userService.createUser(userEntity);
        userEntity.setEmail("testInvalid");
        String loginJson = objectMapper.writeValueAsString(userEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson)
        ).andExpect(
                MockMvcResultMatchers.status().isUnauthorized()
        );
    }
    @Test
    public void testLoginJwtContainsCorrectClaims() throws Exception {

        UserEntity userEntity = TestDataUtil.createTestUserEntityA();
        String loginJson = objectMapper.writeValueAsString(userEntity);
        userService.createUser(userEntity);

        String response = mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(loginJson)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String token = new ObjectMapper().readTree(response).get("token").asText();

        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        assertEquals(userEntity.getEmail(), claims.getSubject());
        assertEquals(userEntity.getRole(), claims.get("role", String.class));
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
        assertTrue(claims.getExpiration().after(claims.getIssuedAt()));
    }
}
