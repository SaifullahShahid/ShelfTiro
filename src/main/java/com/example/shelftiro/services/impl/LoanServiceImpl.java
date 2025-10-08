package com.example.shelftiro.services.impl;

import com.example.shelftiro.domain.entities.BookEntity;
import com.example.shelftiro.domain.entities.LoanEntity;
import com.example.shelftiro.domain.entities.UserEntity;
import com.example.shelftiro.repositories.BookRepository;
import com.example.shelftiro.repositories.LoanRepository;
import com.example.shelftiro.repositories.UserRepository;
import com.example.shelftiro.services.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
public class LoanServiceImpl implements LoanService {

    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    private final LoanRepository loanRepository;

    public LoanServiceImpl(BookRepository bookRepository, UserRepository userRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
    }


    @Override
    public LoanEntity createLoan(Long bookId, LoanEntity loanEntity) {
        boolean exists = loanRepository.
                existsByBookEntity_IdAndUserEntity_IdAndReturnDateIsNull(
                        bookId,loanEntity.getUserEntity().getId()
                );
        if (exists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This book is already loaned out to the user.");
        }
        System.out.println("bookId = " + bookId);
        System.out.println("loanEntity.userEntity = " + loanEntity.getUserEntity().getId());
        if (!bookRepository.existsById(bookId)|| !userRepository.existsById(loanEntity.getUserEntity().getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Book or User does not exist");
        }
        BookEntity book = bookRepository.findById(bookId).get();
        UserEntity user = userRepository.findById(loanEntity.getUserEntity().getId()).get();
        loanEntity.setBookEntity(book);
        loanEntity.setUserEntity(user);
        loanEntity.setDueDate(LocalDate.now().plusDays(14));
        return loanRepository.save(loanEntity);
    }
}
