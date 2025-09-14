package com.example.shelftiro.mappers;

public interface AuthorMapper <A,B> {

    B mapToAuthorEntity(A a);

    A mapFromAuthorDto(B b);
}
