package com.example.shelftiro.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanResponseDto {

    private Long id;

    private LocalDate loanDate;

    private LocalDate dueDate;

    private LocalDate returnDate;

    private UserRequestDto user;

    private BookRequestDto book;
}
