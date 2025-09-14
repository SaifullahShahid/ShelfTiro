package com.example.shelftiro.mappers;

public interface UserMapper <A,B> {

    B mapToUserEntity(A a);

    A mapFromUserDto(B b);
}
