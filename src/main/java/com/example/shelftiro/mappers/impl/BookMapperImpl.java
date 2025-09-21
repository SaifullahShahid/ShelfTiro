package com.example.shelftiro.mappers.impl;

import com.example.shelftiro.domain.dto.BookRequestDto;
import com.example.shelftiro.domain.dto.BookResponseDto;
import com.example.shelftiro.domain.entities.BookEntity;
import com.example.shelftiro.mappers.BookMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements BookMapper<BookEntity, BookRequestDto, BookResponseDto> {

    private final ModelMapper modelMapper;

    public BookMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
    @Override
    public BookResponseDto mapToBookResponseDto(BookEntity bookEntity) {
        return modelMapper.map(bookEntity, BookResponseDto.class);
    }

    @Override
    public BookEntity mapFromBookRequestDto(BookRequestDto bookRequestDto) {
        return modelMapper.map(bookRequestDto, BookEntity.class);
    }



}
