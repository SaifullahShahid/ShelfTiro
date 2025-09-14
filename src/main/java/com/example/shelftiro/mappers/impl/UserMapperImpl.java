package com.example.shelftiro.mappers.impl;

import com.example.shelftiro.domain.dto.UserDto;
import com.example.shelftiro.domain.entities.UserEntity;
import com.example.shelftiro.mappers.UserMapper;
import org.modelmapper.ModelMapper;

public class UserMapperImpl implements UserMapper <UserEntity, UserDto> {

    private ModelMapper modelMapper;

    public UserMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }


    @Override
    public UserDto mapToUserEntity(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserEntity mapFromUserDto(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }
}
