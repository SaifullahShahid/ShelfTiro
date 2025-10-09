package com.example.shelftiro.repositories;

import com.example.shelftiro.domain.entities.BookEntity;
import com.example.shelftiro.domain.entities.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository <LoanEntity, Long> {

    // Checks if a specific user has already loaned a specific book and not returned it
    boolean existsByUserEntity_IdAndBookEntity_IdAndReturnDateIsNull(Long userId, Long bookId);

    List<LoanEntity> findByUserEntity_Id(Long userId);
}
