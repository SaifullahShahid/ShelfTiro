package com.example.shelftiro.controllers;

import com.example.shelftiro.domain.dto.UserDto;
import com.example.shelftiro.domain.entities.UserEntity;
import com.example.shelftiro.mappers.UserMapper;
import com.example.shelftiro.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
public class UserController {

    private final UserService userService;
    private final UserMapper<UserEntity,UserDto> userMapper;

    public UserController(UserService userService, UserMapper<UserEntity,UserDto> userMapper){
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping(path = "/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserEntity userEntity = userMapper.mapFromUserDto(userDto);
        UserEntity savedUserEntity = userService.createUser(userEntity);
        return new ResponseEntity<>(userMapper.mapToUserDto(savedUserEntity), HttpStatus.CREATED);
    }

    @DeleteMapping(path="/users/{id}")
    public ResponseEntity <Void> deleteUser(@PathVariable("id") Long id){
        boolean deleted = userService.deleteUser(id);
        if(!deleted){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
