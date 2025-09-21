package com.example.shelftiro.mappers.impl;

import com.example.shelftiro.domain.dto.AuthorRequestDto;
import com.example.shelftiro.domain.dto.AuthorResponseDto;
import com.example.shelftiro.domain.entities.AuthorEntity;
import com.example.shelftiro.mappers.AuthorMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapperImpl implements AuthorMapper<AuthorEntity, AuthorResponseDto,AuthorRequestDto> {

    private final ModelMapper modelMapper;

    public AuthorMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
    @Override
    public AuthorResponseDto mapToAuthorResponseDto(AuthorEntity authorEntity) {
        return modelMapper.map(authorEntity, AuthorResponseDto.class);
    }

    @Override
    public AuthorEntity mapFromAuthorRequestDto(AuthorRequestDto authorRequestDto) {
        return modelMapper.map(authorRequestDto, AuthorEntity.class);
    }
}
