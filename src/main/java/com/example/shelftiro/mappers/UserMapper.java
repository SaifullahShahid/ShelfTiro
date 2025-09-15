package com.example.shelftiro.mappers;

public interface UserMapper <A,B> {

    B mapToUserDto(A a);

    A mapFromUserDto(B b);
}
