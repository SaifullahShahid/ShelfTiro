package com.example.shelftiro.mappers;

public interface LoanMapper <A,B> {

    B mapToLoanEntity(A a);


    A mapFromLoanDto(B b);
}
