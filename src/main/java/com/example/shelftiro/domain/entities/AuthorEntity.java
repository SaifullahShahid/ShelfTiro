package com.example.shelftiro.domain.entities;

import jakarta.persistence.*;
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
@Entity
@Table(name = "authors")
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long id;

    @Column(name = "author_name",nullable = false)
    private String name;

    @Column(name = "author_birth_date")
    private LocalDate birthDate;

    @Column(name = "author_country_of_origin")
    private String countryOrigin;

    @OneToMany(mappedBy = "authorEntity")
    private List<BookEntity> booksEntity;
}
