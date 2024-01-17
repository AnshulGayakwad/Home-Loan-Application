package com.hla.in.homeloanapplication.service;



import com.hla.in.homeloanapplication.dtos.LoanApplicationDto;
import com.hla.in.homeloanapplication.entities.LoanApplication;
import com.hla.in.homeloanapplication.enums.Status;
import com.hla.in.homeloanapplication.exceptions.CouldNotBeAddedException;
import com.hla.in.homeloanapplication.exceptions.ResourceNotFoundException;

import java.util.List;


public interface ILoanApplicationService {
    LoanApplication deleteLoanApplicationId(long loanApplicationId) throws ResourceNotFoundException;
    List<LoanApplication> retrieveAllLoanApplication();
    LoanApplication retrieveLoanApplicationById(Long loanApplicationId) throws ResourceNotFoundException;
    LoanApplication addLoanApplication(LoanApplicationDto loanApplication) throws ResourceNotFoundException, CouldNotBeAddedException;
    LoanApplication updateLoanApplication(long id) throws ResourceNotFoundException;
    LoanApplication updateStatusOfLoanApplication(Long loanApplicationId, Status status) throws ResourceNotFoundException;
    List<LoanApplication> retrieveLoanApplicationByStatus(String status);

}
