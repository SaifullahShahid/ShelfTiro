package com.example.shelftiro.mappers.impl;

import com.example.shelftiro.domain.dto.AuthorDto;
import com.example.shelftiro.domain.dto.BookDto;
import com.example.shelftiro.domain.entities.AuthorEntity;
import com.example.shelftiro.mappers.AuthorMapper;
import org.modelmapper.ModelMapper;

public class AuthorMapperImpl implements AuthorMapper<AuthorEntity, AuthorDto> {

    private ModelMapper modelMapper;

    public AuthorMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
    @Override
    public AuthorDto mapToAuthorEntity(AuthorEntity authorEntity) {
        return modelMapper.map(authorEntity, AuthorDto.class);
    }

    @Override
    public AuthorEntity mapFromAuthorDto(AuthorDto authorDto) {
        return modelMapper.map(authorDto, AuthorEntity.class);
    }
}
