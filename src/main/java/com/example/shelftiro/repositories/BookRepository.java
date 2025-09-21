package com.example.shelftiro.repositories;

import com.example.shelftiro.domain.entities.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository <BookEntity, Long> {


    boolean existsByIsbn(String isbn);

    @Query("SELECT b FROM BookEntity b WHERE b.isbn = :isbn")
    Optional<BookEntity> getByIsbn (@Param("isbn") String isbn);

    Page<BookEntity> findByIsbn(String isbn, Pageable pageable);

    Page<BookEntity> findByAuthorEntity_Id(Long id, Pageable pageable);

    Page<BookEntity> findByTitleIgnoreCase(String title, Pageable pageable);

    Page<BookEntity> findByGenreIgnoreCase(String genre, Pageable pageable);

    Page<BookEntity> findByAuthorEntity_NameIgnoreCase(String authorName, Pageable pageable);

    Page<BookEntity> findByGenreIgnoreCaseAndAuthorEntity_NameIgnoreCase(String genre, String authorName, Pageable pageable);

    Page<BookEntity> findByTitleIgnoreCaseOrAuthorEntity_NameIgnoreCase(String title, String authorName, Pageable pageable);

    Page<BookEntity> findByTitleIgnoreCaseAndGenreIgnoreCase(String title, String genre, Pageable pageable);


}
