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
public class AuthorResponseDto {

    private Long id;

    private String name;

    private LocalDate birthDate;

    private String countryOrigin;

    private List<BookRequestDto> books;
}
