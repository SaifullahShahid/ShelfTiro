package com.example.shelftiro.services;

import com.example.shelftiro.domain.entities.AuthorEntity;
import com.example.shelftiro.domain.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserEntity createUser(UserEntity userEntity);

    void deleteUser(Long id);

    List<UserEntity> listUsers();


    UserEntity listUserById(Long id);

    UserEntity fullUpdateUser(Long id, UserEntity userEntity);

    UserEntity partialUpdateUser(Long id, UserEntity userEntity);

}
