package com.example.shelftiro.services;

import com.example.shelftiro.domain.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserEntity createUser(UserEntity userEntity);

    boolean deleteUser(Long id);

    List<UserEntity> listUsers();


    Optional<UserEntity> listUserById(Long id);

    Optional<UserEntity> fullUpdateUser(Long id, UserEntity userEntity);

}
