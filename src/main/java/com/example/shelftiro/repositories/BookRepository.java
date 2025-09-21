package com.example.shelftiro.repositories;

import com.example.shelftiro.domain.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository <BookEntity, Long> {


    boolean existsByIsbn(String isbn);

    Optional<BookEntity> findByIsbn(String isbn);
}
