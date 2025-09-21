package com.example.shelftiro.mappers;

public interface BookMapper <A,B,C> {

    C mapToBookResponseDto(A a);

    A mapFromBookRequestDto(B b);
}
