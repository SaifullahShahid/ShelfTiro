package com.example.shelftiro.mappers.impl;

import com.example.shelftiro.domain.dto.AuthorDto;
import com.example.shelftiro.domain.entities.AuthorEntity;
import com.example.shelftiro.mappers.AuthorMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapperImpl implements AuthorMapper<AuthorEntity, AuthorDto> {

    private final ModelMapper modelMapper;

    public AuthorMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
    @Override
    public AuthorDto mapToAuthorDto(AuthorEntity authorEntity) {
        return modelMapper.map(authorEntity, AuthorDto.class);
    }

    @Override
    public AuthorEntity mapFromAuthorDto(AuthorDto authorDto) {
        return modelMapper.map(authorDto, AuthorEntity.class);
    }
}
