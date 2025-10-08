package com.example.shelftiro.repositories;

import com.example.shelftiro.domain.entities.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository <LoanEntity, Long> {

    // Checks if a specific user has already loaned a specific book and not returned it
    boolean existsByBookEntity_IdAndUserEntity_IdAndReturnDateIsNull(Long bookId, Long userId);
}
