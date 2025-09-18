package com.example.shelftiro.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "book_isbn",nullable = false)
    private String isbn;

    @Column(name = "book_title", nullable = false)
    private String title;

    @Column(name = "book_genre",nullable = false)
    private String genre;

    @ManyToOne()
    @JoinColumn(name = "author_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_book_author"))
    private AuthorEntity authorEntity;
}
