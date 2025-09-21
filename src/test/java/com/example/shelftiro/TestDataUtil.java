package com.example.shelftiro;

import com.example.shelftiro.domain.dto.UserDto;
import com.example.shelftiro.domain.entities.AuthorEntity;
import com.example.shelftiro.domain.entities.BookEntity;
import com.example.shelftiro.domain.entities.UserEntity;

import java.time.LocalDate;

public class TestDataUtil {

    private TestDataUtil(){}

    public static UserEntity createTestUserEntityA(){
        return UserEntity.builder()
                .name("Saif")
                .email("saif@gmail.com")
                .age(21)
                .build();
    }

    public static UserDto createTestUserDtoA(){
        return UserDto.builder()
                .name("Saif")
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
    public static AuthorEntity createTestAuthorEntityA(){
        return AuthorEntity.builder()
                .name("J.K. Rowling")
                .birthDate(LocalDate.parse("1965-07-31"))
                .countryOrigin("United Kingdom")
                .build();
    }
    public static BookEntity createTestBookEntityA(){
        return BookEntity.builder()
                .isbn("11111")
                .title("Harry Potter And The Philosopher's Stone")
                .genre("Fantasy Fiction")
                .build();
    }
}
