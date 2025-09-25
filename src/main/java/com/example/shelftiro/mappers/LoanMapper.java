package com.example.shelftiro.mappers;

public interface LoanMapper <A,B,C> {

    B mapToLoanResponseDto(A a);


    A mapFromLoanRequestDto(C c);
}
