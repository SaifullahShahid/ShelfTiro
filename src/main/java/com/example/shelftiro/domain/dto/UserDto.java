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
public class UserDto {


    private Long id;

    private String name;

    private String email;

    private Integer age;

    private LocalDate createdDate;


}
