package com.example.shelftiro.mappers.impl;

import com.example.shelftiro.domain.dto.LoanDto;
import com.example.shelftiro.domain.entities.LoanEntity;
import com.example.shelftiro.mappers.LoanMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class LoanMapperImpl implements LoanMapper <LoanEntity, LoanDto> {

    private ModelMapper modelMapper;

    public LoanMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }


    @Override
    public LoanDto mapToLoanEntity(LoanEntity loanEntity) {
        return modelMapper.map(loanEntity, LoanDto.class);
    }

    @Override
    public LoanEntity mapFromLoanDto(LoanDto loanDto) {
        return modelMapper.map(loanDto, LoanEntity.class);
    }
}
