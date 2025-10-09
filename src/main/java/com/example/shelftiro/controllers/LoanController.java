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

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class LoanController {

    private final LoanService loanService;
    private final LoanMapper<LoanEntity, LoanResponseDto, LoanRequestDto> loanMapper;


    public LoanController(LoanService loanService, LoanMapper<LoanEntity, LoanResponseDto, LoanRequestDto> loanMapper) {
        this.loanService = loanService;
        this.loanMapper = loanMapper;
    }

    @PostMapping("/users/{user_id}/loans")       //create loan by user id and book id
    public ResponseEntity<LoanResponseDto> createLoan(@PathVariable("user_id") Long userId,
                                                      @Valid @RequestBody LoanRequestDto LoanRequestDto){
        LoanEntity loanEntity = loanMapper.mapFromLoanRequestDto(LoanRequestDto);
        return new ResponseEntity<>(
                loanMapper.mapToLoanResponseDto(loanService.createLoan(userId,loanEntity)),
                HttpStatus.CREATED);
    }

    @GetMapping("/users/{user_id}/loans")       //get loan by user id
    public List<LoanResponseDto> listLoansByUserId (@PathVariable("user_id") Long userid){
        return loanService.listUserById(userid).stream()
                .map(loanMapper::mapToLoanResponseDto)
                .toList();
    }

    @PatchMapping("/loans/{loan_id}")           //update loan's return date by loan id
    public ResponseEntity<LoanResponseDto> returnBook(@PathVariable("loan_id") Long loanid){
        LoanResponseDto loanResponseDto = loanMapper.mapToLoanResponseDto(loanService.returnLoan(loanid));
        return new ResponseEntity<>(loanResponseDto,HttpStatus.OK);
    }
    @DeleteMapping(("/loans/{loan_id}"))        //delete loan by loan id
    public ResponseEntity<Void> deleteLoan(@PathVariable("loan_id") Long loanid){
        loanService.deleteLoan(loanid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
