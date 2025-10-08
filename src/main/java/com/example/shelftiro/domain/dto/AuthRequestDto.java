package com.example.shelftiro.domain.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDto {

    @NotBlank(message = "Enter your Email")
    private String email;
    @NotBlank(message = "Enter your Password ")
    private String password;
}
