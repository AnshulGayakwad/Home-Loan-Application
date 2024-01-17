package com.hla.in.homeloanapplication.service.impl;


import com.hla.in.homeloanapplication.dtos.FinanceVerificationOfficerDto;
import com.hla.in.homeloanapplication.entities.FinanceVerificationOfficer;
import com.hla.in.homeloanapplication.exceptions.CouldNotBeAddedException;
import com.hla.in.homeloanapplication.repository.IFinanceVerificationRepository;
import com.hla.in.homeloanapplication.repository.ILoanApplicationRepository;
import com.hla.in.homeloanapplication.service.IFinanceVerificationService;
import com.hla.in.homeloanapplication.exceptions.*;
import com.hla.in.homeloanapplication.entities.*;
import com.hla.in.homeloanapplication.enums.Status;

import com.hla.in.homeloanapplication.util.HomeLoanBorrowingAmountCalculator;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class IFinanceVerificationServiceImpl implements IFinanceVerificationService {

    Log logger = LogFactory.getLog(IFinanceVerificationServiceImpl.class);
    @Autowired
    IFinanceVerificationRepository financeVerificationRepository;

    @Autowired
    ILoanApplicationRepository loanApplicationRepository;

    @Override
    public FinanceVerificationOfficer addFinanceVerificationOfficer(FinanceVerificationOfficerDto financeVerificationDto) throws CouldNotBeAddedException {
        logger.info("Entered in addFinanceVerificationOfficer method in IFinanceVerificationServiceImpl");
        if (financeVerificationRepository.findByFinOfficerContact(financeVerificationDto.getFinOfficerContact()) != null) {
            throw new CouldNotBeAddedException("Officer already exists");
        }
        FinanceVerificationOfficer financeVerificationOfficer = new FinanceVerificationOfficer();
        BeanUtils.copyProperties(financeVerificationDto, financeVerificationOfficer);
        financeVerificationOfficer.setRole("FinanceVerificationOfficer");

        return financeVerificationRepository.save(financeVerificationOfficer);

    }

    /*
    Finance Verification Officer is checking the eligibility of customer
    based on annual income and expenses
    and updating the loan application status accordingly.
     */
    @Override
    public LoanApplication updateStatus(Long id) throws ResourceNotFoundException {

        logger.info("Entered in updateStatus method in IFinanceVerificationServiceImpl");

        LoanApplication loanApplicationOp = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not found for Id " + id));
        if (loanApplicationOp.getStatus().equals(Status.WAITING_FOR_FINANCE_APPROVAL)) {
            LoanApplication loanApplication = loanApplicationOp;
            HomeLoanBorrowingAmountCalculator homeLoanBorrowingAmountCalculator =
                    new HomeLoanBorrowingAmountCalculator(loanApplication.getLoanAppliedAmount()
                            , loanApplication.getScheme().getInterestRate(), loanApplication.getScheme().getTenure()
                            , loanApplication.getTotalAnnualIncome(), loanApplication.getMonthlyExpenses()
                            , loanApplication.getOtherMonthlyExpenses());

            double acceptedAmount = homeLoanBorrowingAmountCalculator.getHomeLoanBorrowingAmount();
            loanApplication.setLoanApprovedAmount(acceptedAmount);
            if (acceptedAmount == 0.0) {
                loanApplication.setFinanceVerificationApproval(false);
                loanApplication.setStatus(Status.REJECTED);
            } else {
                loanApplication.setFinanceVerificationApproval(true);
                loanApplication.setStatus(Status.PENDING);
            }
            loanApplicationRepository.save(loanApplication);
            return loanApplication;
        } else {
            throw new ResourceNotFoundException("This application is not under your authority");
        }
    }
}
