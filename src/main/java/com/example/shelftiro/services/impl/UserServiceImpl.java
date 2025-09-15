package com.example.shelftiro.services.impl;

import com.example.shelftiro.domain.entities.UserEntity;
import com.example.shelftiro.repositories.UserRepository;
import com.example.shelftiro.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserEntity createUser(UserEntity userEntity) {

        return userRepository.save(userEntity);
    }
}
