package com.example.shelftiro.domain.dto;

import com.example.shelftiro.domain.entities.BookEntity;
import com.example.shelftiro.domain.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanDto {


    private Long id;

    private LocalDate loanDate;

    private LocalDate dueDate;

    private LocalDate returnDate;


    private UserDto userDto;


    private BookDto bookDto;
}
