package com.example.shelftiro.mappers;

public interface AuthorMapper <A,B,C> {

    B mapToAuthorResponseDto(A a);

    A mapFromAuthorRequestDto(C c);
}
