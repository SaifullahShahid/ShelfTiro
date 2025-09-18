package com.example.shelftiro.services;

import com.example.shelftiro.domain.entities.AuthorEntity;
import com.example.shelftiro.domain.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserEntity createUser(UserEntity userEntity);

    boolean deleteUser(Long id);

    List<UserEntity> listUsers();


    UserEntity listUserById(Long id);

    Optional<UserEntity> fullUpdateUser(Long id, UserEntity userEntity);

    Optional<UserEntity> partialUpdateUser(Long id, UserEntity userEntity);

}
