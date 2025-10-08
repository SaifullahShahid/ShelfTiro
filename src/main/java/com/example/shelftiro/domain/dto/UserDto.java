package com.example.shelftiro.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;

    @NotBlank(message = "Name must not be blank!")
    private String name;

    @NotBlank(message = "Email must not be blank")
    private String email;

    private Integer age;

    private String password;

    private String role;

    private LocalDate createdDate;

    private List<LoanResponseDto> loans;


}
