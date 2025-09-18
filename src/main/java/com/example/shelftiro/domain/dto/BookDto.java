package com.example.shelftiro.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {

    private Long id;

    private String isbn;

    private String title;

    private String genre;

    private AuthorDto author;
}
