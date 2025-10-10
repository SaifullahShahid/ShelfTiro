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
import java.util.List;

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
    public LoanEntity createLoan(Long userId, LoanEntity loanEntity) {
        if(loanEntity.getBookEntity()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Book is required");
        }
        if (loanEntity.getBookEntity().getId()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Book id is null");
        }
        boolean exists = loanRepository.
                existsByUserEntity_IdAndBookEntity_IdAndReturnDateIsNull(
                        userId,loanEntity.getBookEntity().getId()
                );
        if (exists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This book is already loaned out to the user.");
        }
        if (!userRepository.existsById(userId)|| !bookRepository.existsById(loanEntity.getBookEntity().getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Book or User does not exist");
        }
        BookEntity book = bookRepository.findById(loanEntity.getBookEntity().getId()).get();
        UserEntity user = userRepository.findById(userId).get();
        loanEntity.setBookEntity(book);
        loanEntity.setUserEntity(user);
        loanEntity.setDueDate(LocalDate.now().plusDays(14));
        return loanRepository.save(loanEntity);
    }

    @Override
    public List<LoanEntity> listUserById(Long userid) {
        if (!userRepository.existsById(userid)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist by this id!");
        }
        return loanRepository.findByUserEntity_Id(userid);
    }

    @Override
    public LoanEntity returnLoan(Long loanid) {
        LoanEntity loanEntity= loanRepository.findById(loanid)
                .orElseThrow(()-> new ResponseStatusException
                        (HttpStatus.NOT_FOUND,"Loan does not exist by this id!"));

        if (loanEntity.getReturnDate()!=null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Book already returned!");
        }
        loanEntity.setReturnDate(LocalDate.now());
        return loanRepository.save(loanEntity);
    }

    @Override
    public void deleteLoan(Long loanid) {
        if(!loanRepository.existsById(loanid)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Loan does not exist by this id!");
        }
        loanRepository.deleteById(loanid);
    }
}
