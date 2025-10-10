package com.example.shelftiro.services;

import com.example.shelftiro.domain.dto.LoanResponseDto;
import com.example.shelftiro.domain.entities.LoanEntity;

import java.util.List;

public interface LoanService {
    LoanEntity createLoan(Long userId, LoanEntity loanEntity);

    List<LoanEntity> listUserById(Long userid);


    LoanEntity returnLoan(Long loanid);

    void deleteLoan(Long loanid);
}
