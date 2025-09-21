package com.example.shelftiro.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorRequestDto {

    @NotBlank(message = "Author name is required")
    private String name;

    private LocalDate birthDate;

    private String countryOrigin;
}
