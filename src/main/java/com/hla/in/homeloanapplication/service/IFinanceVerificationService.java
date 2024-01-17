package com.hla.in.homeloanapplication.service;


import com.hla.in.homeloanapplication.dtos.FinanceVerificationOfficerDto;
import com.hla.in.homeloanapplication.dtos.UserLoginDto;
import com.hla.in.homeloanapplication.entities.FinanceVerificationOfficer;
import com.hla.in.homeloanapplication.entities.LoanApplication;
import com.hla.in.homeloanapplication.exceptions.CouldNotBeAddedException;
import com.hla.in.homeloanapplication.exceptions.InvalidCredentialException;
import com.hla.in.homeloanapplication.exceptions.ResourceNotFoundException;

public interface IFinanceVerificationService {
    LoanApplication updateStatus(Long id) throws ResourceNotFoundException;

    FinanceVerificationOfficer addFinanceVerificationOfficer(FinanceVerificationOfficerDto financeVerificationDto) throws CouldNotBeAddedException;

}
