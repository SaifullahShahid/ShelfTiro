package com.example.shelftiro.mappers;

public interface BookMapper <A,B> {

    B mapToBookEntity(A a);

    A mapFromBookDto(B b);
}
