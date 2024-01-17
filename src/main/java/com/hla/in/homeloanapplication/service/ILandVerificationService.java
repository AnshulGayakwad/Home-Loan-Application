package com.hla.in.homeloanapplication.service;


import com.hla.in.homeloanapplication.dtos.LandVerificationOfficerDto;
import com.hla.in.homeloanapplication.dtos.UserLoginDto;
import com.hla.in.homeloanapplication.entities.LandVerificationOfficer;
import com.hla.in.homeloanapplication.entities.LoanApplication;
import com.hla.in.homeloanapplication.exceptions.CouldNotBeAddedException;
import com.hla.in.homeloanapplication.exceptions.InvalidCredentialException;
import com.hla.in.homeloanapplication.exceptions.ResourceNotFoundException;

public interface ILandVerificationService {
    void updateStatus(LoanApplication loanApplication) throws ResourceNotFoundException;

    LandVerificationOfficer addLandVerificationOfficer(LandVerificationOfficerDto landVerificationOfficerDto) throws CouldNotBeAddedException;

}
