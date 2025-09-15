package com.example.shelftiro.repositories;

import com.example.shelftiro.domain.entities.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository <LoanEntity, Long> {

}
