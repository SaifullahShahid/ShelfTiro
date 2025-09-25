package com.example.shelftiro.services;

import com.example.shelftiro.domain.entities.LoanEntity;

public interface LoanService {
    LoanEntity createLoan(Long bookId, LoanEntity loanEntity);
}
