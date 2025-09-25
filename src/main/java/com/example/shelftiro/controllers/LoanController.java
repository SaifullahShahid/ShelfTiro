package com.example.shelftiro.controllers;

import com.example.shelftiro.domain.dto.LoanRequestDto;
import com.example.shelftiro.domain.dto.LoanResponseDto;
import com.example.shelftiro.domain.entities.LoanEntity;
import com.example.shelftiro.mappers.LoanMapper;
import com.example.shelftiro.services.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
public class LoanController {

    private final LoanService loanService;
    private final LoanMapper<LoanEntity, LoanResponseDto, LoanRequestDto> loanMapper;


    public LoanController(LoanService loanService, LoanMapper<LoanEntity, LoanResponseDto, LoanRequestDto> loanMapper) {
        this.loanService = loanService;
        this.loanMapper = loanMapper;
    }

    @PostMapping(path = "/books/{book_id}/loans")
    public ResponseEntity<LoanResponseDto> createLoan(@PathVariable("book_id") Long bookId,
                                                      @Valid @RequestBody LoanRequestDto LoanRequestDto){
        LoanEntity loanEntity = loanMapper.mapFromLoanRequestDto(LoanRequestDto);
        return new ResponseEntity<>(
                loanMapper.mapToLoanResponseDto(loanService.createLoan(bookId,loanEntity)),
                HttpStatus.CREATED);
    }
}
