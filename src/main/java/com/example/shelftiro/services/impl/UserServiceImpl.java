package com.example.shelftiro.services.impl;

import com.example.shelftiro.domain.entities.UserEntity;
import com.example.shelftiro.repositories.UserRepository;
import com.example.shelftiro.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<UserEntity> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity listUserById(Long id) {
        Optional<UserEntity> existingUser = userRepository.findById(id);
        if(existingUser.isPresent()){
            return existingUser.get();

        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User by this id does not exist!");
        }
    }

    @Override
    public Optional<UserEntity> fullUpdateUser(Long id, UserEntity userEntity) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setName(userEntity.getName());
            existingUser.setEmail(userEntity.getEmail());
            existingUser.setAge(userEntity.getAge());
            return userRepository.save(existingUser);
        });
    }

    @Override
    public Optional<UserEntity> partialUpdateUser(Long id, UserEntity userEntity) {
        return userRepository.findById(id).map(existingUser -> {
            Optional.ofNullable(userEntity.getName()).ifPresent(existingUser::setName);
            Optional.ofNullable(userEntity.getEmail()).ifPresent(existingUser::setEmail);
            Optional.ofNullable(userEntity.getAge()).ifPresent(existingUser::setAge);

            return userRepository.save(existingUser);
        });
    }
}
