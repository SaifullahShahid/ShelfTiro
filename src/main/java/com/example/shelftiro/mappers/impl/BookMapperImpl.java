package com.example.shelftiro.mappers.impl;

import com.example.shelftiro.domain.dto.BookDto;
import com.example.shelftiro.domain.dto.LoanDto;
import com.example.shelftiro.domain.entities.BookEntity;
import com.example.shelftiro.mappers.BookMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements BookMapper<BookEntity, BookDto> {

    private ModelMapper modelMapper;

    public BookMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
    @Override
    public BookDto mapToBookEntity(BookEntity bookEntity) {
        return modelMapper.map(bookEntity, BookDto.class);
    }

    @Override
    public BookEntity mapFromBookDto(BookDto bookDto) {
        return modelMapper.map(bookDto, BookEntity.class);
    }
}
