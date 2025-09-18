package com.example.shelftiro.controllers;

import com.example.shelftiro.TestDataUtil;
import com.example.shelftiro.domain.dto.UserDto;
import com.example.shelftiro.domain.entities.UserEntity;
import com.example.shelftiro.services.UserService;
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
public class UserControllerIntegrationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;



    @Test
    public void testThatCreateUserSuccessfullyReturnsHttp201Created() throws Exception{
        UserDto userDto = TestDataUtil.createTestUserDtoA();
        String userJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/users")
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
                MockMvcRequestBuilders.post("/api/users")
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
                MockMvcResultMatchers.jsonPath("$.createdDate").exists()
        );
    }
    @Test
    public void testThatDeleteUserSuccessfullyReturnsHttp204NoContent() throws Exception {

        UserEntity testUserEntityA = TestDataUtil.createTestUserEntityA();
        UserEntity savedUserEntityA = userService.createUser(testUserEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/users/"+ savedUserEntityA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );

    }
    @Test
    public void testThatListUsersSuccessfullyReturnsAllUsers() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatlistUserByIdSuccessfullyReturnsHttp404WhenNoUserIsFound() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/users/-1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatListUserByIdSuccessfullyReturnsDesiredUser() throws Exception {
        UserEntity userEntityA = TestDataUtil.createTestUserEntityA();
        UserEntity savedUserEntityA = userService.createUser(userEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/users/"+savedUserEntityA.getId())
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Saif")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value("saif@gmail.com")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(21)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.createdDate").exists()
        );
    }
    @Test
    public void testThatFullUpdateUserSuccessfullyReturnsHttp200IsOk() throws Exception{
        UserEntity userEntityA = TestDataUtil.createTestUserEntityA();
        userService.createUser(userEntityA);
        userEntityA.setName("TestUpdate");
        String userJson = objectMapper.writeValueAsString(userEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/users/"+userEntityA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
    @Test
    public void testThatFullUpdateUserSuccessfullyReturnsUpdatedUser() throws Exception {
        UserEntity userEntityA = TestDataUtil.createTestUserEntityA();
        userService.createUser(userEntityA);
        userEntityA.setName("TestName");
        userEntityA.setEmail("TestEmail@test.com");
        userEntityA.setAge(1000);
        String userJson = objectMapper.writeValueAsString(userEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/users/"+userEntityA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("TestName")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value("TestEmail@test.com")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(1000)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.createdDate").exists()
        );
    }
    @Test
    public void testThatPartialUpdateUserSuccessfullyReturnsHttp200IsOk() throws Exception{
        UserEntity userEntityA = TestDataUtil.createTestUserEntityA();
        UserEntity savedUserEntityA =  userService.createUser(userEntityA);
        savedUserEntityA.setName("TestUpdate");
        savedUserEntityA.setEmail(null);
        savedUserEntityA.setAge(null);
        String userJson = objectMapper.writeValueAsString(savedUserEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/users/"+savedUserEntityA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
    @Test
    public void testThatPartialUpdateUserSuccessfullyReturnsUpdatedUser() throws Exception{
        UserEntity userEntityA = TestDataUtil.createTestUserEntityA();
        UserEntity savedUserEntityA =  userService.createUser(userEntityA);
        savedUserEntityA.setName("TestUpdate");
        savedUserEntityA.setEmail(null);
        savedUserEntityA.setAge(null);
        String userJson = objectMapper.writeValueAsString(savedUserEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/users/"+savedUserEntityA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("TestUpdate")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value("saif@gmail.com")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(21)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.createdDate").exists()
        );
    }


}
