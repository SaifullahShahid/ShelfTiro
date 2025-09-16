package com.example.shelftiro.controllers;

import com.example.shelftiro.domain.dto.UserDto;
import com.example.shelftiro.domain.entities.UserEntity;
import com.example.shelftiro.mappers.UserMapper;
import com.example.shelftiro.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


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

    @GetMapping(path = "/users")
    public List<UserDto> listUsers(){
        return userService.listUsers().stream()
                .map(userMapper::mapToUserDto)
                .toList();
    }

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<UserDto> listUserById(@PathVariable("id") Long id){
        Optional<UserEntity> foundUser = userService.listUserById(id);
        if(foundUser.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userMapper.mapToUserDto(foundUser.get()),HttpStatus.OK);
    }

    @PutMapping(path = "/users/{id}")
    public ResponseEntity<UserDto> fullUpdateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto){
        UserEntity userEntity = userMapper.mapFromUserDto(userDto);
        Optional<UserEntity> updatedUser = userService.fullUpdateUser(id,userEntity);
        if (updatedUser.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userMapper.mapToUserDto(updatedUser.get()),HttpStatus.OK);
    }

    @PatchMapping(path = "/users/{id}")
    public ResponseEntity<UserDto> partialUpdateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto){
        UserEntity userEntity = userMapper.mapFromUserDto(userDto);
        Optional<UserEntity> updatedUser = userService.partialUpdateUser(id,userEntity);
        if (updatedUser.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userMapper.mapToUserDto(updatedUser.get()),HttpStatus.OK);
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
