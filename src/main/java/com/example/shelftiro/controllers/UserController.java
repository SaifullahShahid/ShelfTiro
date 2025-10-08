package com.example.shelftiro.controllers;

import com.example.shelftiro.domain.dto.UserDto;
import com.example.shelftiro.domain.entities.UserEntity;
import com.example.shelftiro.mappers.UserMapper;
import com.example.shelftiro.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper<UserEntity,UserDto> userMapper;
    public UserController(UserService userService, UserMapper<UserEntity,UserDto> userMapper){
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserDto> listUsers(){       //get all users
        return userService.listUsers().stream()
                .map(userMapper::mapToUserDto)
                .toList();
    }

    @GetMapping(path = "/{id}")         //get user by user id
    public ResponseEntity<UserDto> listUserById(@PathVariable("id") Long id){
        return new ResponseEntity<>(userMapper.mapToUserDto(userService.listUserById(id)),HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")        //full update user by user id
    public ResponseEntity<UserDto> fullUpdateUser(@PathVariable("id") Long id,
                                                  @Valid @RequestBody UserDto userDto){
        UserEntity userEntity = userMapper.mapFromUserDto(userDto);
        return new ResponseEntity<>(userMapper.mapToUserDto(userService.fullUpdateUser(id,userEntity)),HttpStatus.OK);

    }

    @PatchMapping(path = "/{id}")      //partial update user by user id
    public ResponseEntity<UserDto> partialUpdateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto){
        UserEntity userEntity = userMapper.mapFromUserDto(userDto);
        return new ResponseEntity<>(userMapper.mapToUserDto(userService.partialUpdateUser(id,userEntity)),HttpStatus.OK);
    }


    @DeleteMapping(path="/{id}")     //delete user by user id
    public void deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
    }
}
