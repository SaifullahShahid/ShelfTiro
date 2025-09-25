package com.example.shelftiro.mappers.impl;

import com.example.shelftiro.domain.dto.LoanRequestDto;
import com.example.shelftiro.domain.dto.LoanResponseDto;
import com.example.shelftiro.domain.entities.LoanEntity;
import com.example.shelftiro.mappers.LoanMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class LoanMapperImpl implements LoanMapper <LoanEntity, LoanResponseDto, LoanRequestDto> {

    private final ModelMapper modelMapper;

    public LoanMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
        this.modelMapper.typeMap(LoanRequestDto.class, LoanEntity.class)
                .addMappings(mapper -> mapper.skip(LoanEntity::setId));
    }


    @Override
    public LoanResponseDto mapToLoanResponseDto(LoanEntity loanEntity) {
        return modelMapper.map(loanEntity, LoanResponseDto.class);

    }

    @Override
    public LoanEntity mapFromLoanRequestDto(LoanRequestDto loanDto) {
        return modelMapper.map(loanDto, LoanEntity.class);
    }
}
