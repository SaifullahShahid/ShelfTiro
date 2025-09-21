package com.example.shelftiro.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequestDto {

    @NotBlank(message = "Book ISBN must not be blank!")
    private String isbn;

    @NotBlank(message = "Book title must not be blank!")
    private String title;

    private String genre;

}
