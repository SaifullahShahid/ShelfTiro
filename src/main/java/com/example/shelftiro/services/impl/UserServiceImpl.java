package com.example.shelftiro.services.impl;

import com.example.shelftiro.domain.entities.UserEntity;
import com.example.shelftiro.repositories.UserRepository;
import com.example.shelftiro.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public boolean deleteUser(Long id) {
        Optional <UserEntity> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
