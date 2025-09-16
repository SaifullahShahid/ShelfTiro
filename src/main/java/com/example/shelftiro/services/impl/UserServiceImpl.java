package com.example.shelftiro.services.impl;

import com.example.shelftiro.domain.entities.UserEntity;
import com.example.shelftiro.repositories.UserRepository;
import com.example.shelftiro.services.UserService;
import org.springframework.stereotype.Service;
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
    public Optional<UserEntity> listUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<UserEntity> fullUpdateUser(Long id, UserEntity userEntity) {
        Optional<UserEntity> updatedUser= userRepository.findById(id);
        if(updatedUser.isPresent()){
            userEntity.setId(id);
            userEntity.setCreatedDate(updatedUser.get().getCreatedDate());
            return Optional.of(userRepository.save(userEntity));
        }
        return Optional.empty();

    }
}
