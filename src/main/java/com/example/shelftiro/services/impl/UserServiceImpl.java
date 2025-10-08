package com.example.shelftiro.services.impl;

import com.example.shelftiro.domain.entities.UserEntity;
import com.example.shelftiro.repositories.UserRepository;
import com.example.shelftiro.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserEntity createUser(UserEntity userEntity) {
        if (userRepository.findByEmail(userEntity.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Email already registered");
        }
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        if(userEntity.getRole()==null){
            userEntity.setRole("ROLE_USER");
        }
        return userRepository.save(userEntity);
    }

    @Override
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User by this id does not exist!");
        }
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
    public UserEntity fullUpdateUser(Long id, UserEntity userEntity) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setName(userEntity.getName());
            existingUser.setEmail(userEntity.getEmail());
            existingUser.setAge(userEntity.getAge());
            return userRepository.save(existingUser);
        }).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User by this id does not exist!"));
    }

    @Override
    public UserEntity partialUpdateUser(Long id, UserEntity userEntity) {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User by this id does not exist!"));

        if (userEntity.getName() != null && !userEntity.getName().isBlank()) {
            existingUser.setName(userEntity.getName());
        }

        if (userEntity.getEmail() != null && !userEntity.getEmail().isBlank()) {
            existingUser.setEmail(userEntity.getEmail());
        }

        if (userEntity.getAge() != null) {
            existingUser.setAge(userEntity.getAge());
        }
        return userRepository.save(existingUser);
    }

}
