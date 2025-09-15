package com.example.shelftiro;

import com.example.shelftiro.domain.dto.UserDto;
import com.example.shelftiro.domain.entities.UserEntity;

public class TestDataUtil {

    private TestDataUtil(){}

    public static UserDto createTestUserDtoA(){
        return UserDto.builder()
                .name("Saifullah Shahid")
                .email("saif@gmail.com")
                .age(21)
                .build();
    }
    public static UserDto createTestUserDtoB(){
        return UserDto.builder()
                .name("Devtiro")
                .email("devtiro@gmail.com")
                .age(30)
                .build();
    }
    public static UserDto createTestUserDtoC(){
        return UserDto.builder()
                .name("Kanye")
                .email("kanye@gmail.com")
                .age(40)
                .build();
    }
}
