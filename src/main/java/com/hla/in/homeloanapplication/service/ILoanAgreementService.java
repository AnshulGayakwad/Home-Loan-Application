package com.hla.in.homeloanapplication.service;



import com.hla.in.homeloanapplication.entities.LoanAgreement;
import com.hla.in.homeloanapplication.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ILoanAgreementService {

    List<LoanAgreement> retrieveAllLoanAgreement();

    LoanAgreement retrieveAgreementById(long loanAgreementId) throws ResourceNotFoundException;

    LoanAgreement addLoanAgreement(LoanAgreement loanAgreement);

    LoanAgreement updateLoanAgreement(LoanAgreement loanAgreement);
}
