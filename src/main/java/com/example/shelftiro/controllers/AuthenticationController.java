package com.example.shelftiro.controllers;

import com.example.shelftiro.domain.dto.AuthRequestDto;
import com.example.shelftiro.domain.dto.AuthResponseDto;
import com.example.shelftiro.domain.dto.UserDto;
import com.example.shelftiro.domain.entities.UserEntity;
import com.example.shelftiro.mappers.UserMapper;
import com.example.shelftiro.services.JwtService;
import com.example.shelftiro.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final UserMapper<UserEntity, UserDto> userMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto registerRequestDto){
        UserEntity userEntity = userMapper.mapFromUserDto(registerRequestDto);
        UserEntity savedUserEntity = userService.createUser(userEntity);
        return new ResponseEntity<>(userMapper.mapToUserDto(savedUserEntity),HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> loginUser(@Valid @RequestBody AuthRequestDto authRequestDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(),authRequestDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateToken(
                (UserDetails)
                authentication.getPrincipal()
        );

        return new ResponseEntity<>(new AuthResponseDto(jwt),HttpStatus.OK);
    }
}
