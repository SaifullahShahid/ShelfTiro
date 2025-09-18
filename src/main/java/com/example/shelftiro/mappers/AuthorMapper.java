package com.example.shelftiro.mappers;

public interface AuthorMapper <A,B> {

    B mapToAuthorDto(A a);

    A mapFromAuthorDto(B b);
}
