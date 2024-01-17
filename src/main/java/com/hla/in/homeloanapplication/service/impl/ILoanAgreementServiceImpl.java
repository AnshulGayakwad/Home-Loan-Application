package com.hla.in.homeloanapplication.service.impl;


import com.hla.in.homeloanapplication.entities.LoanAgreement;
import com.hla.in.homeloanapplication.exceptions.ResourceNotFoundException;
import com.hla.in.homeloanapplication.repository.ILoanAgreementRepository;
import com.hla.in.homeloanapplication.service.ILoanAgreementService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class ILoanAgreementServiceImpl implements ILoanAgreementService {
    Log logger = LogFactory.getLog(ILoanAgreementServiceImpl.class);

    @Autowired
    ILoanAgreementRepository loanAggRepo;

    /*
    Retrieve All the Loan Agreements which are in the database
     */
    @Override
    public List<LoanAgreement> retrieveAllLoanAgreement() {
        logger.info("Entered into retrieveAllLoanAgreement method");
        return loanAggRepo.findAll();

    }


    /*
    Retrieve Loan Agreement through Loan Agreement Id from the database
     */
    @Override
    public LoanAgreement retrieveAgreementById(long loanAgreementId) throws ResourceNotFoundException {
        logger.info("Entered into retrieveAgreementById method" + loanAgreementId);

        return loanAggRepo.findById(loanAgreementId).orElseThrow(() -> new ResourceNotFoundException("Loan Agreement does not exists with id : " + loanAgreementId));
    }

    @Override
    public LoanAgreement addLoanAgreement(LoanAgreement loanAgreement) {
        logger.info("Entered into addLoanAgreement method");
        return loanAggRepo.save(loanAgreement);
    }

    @Override
    public LoanAgreement updateLoanAgreement(@Valid LoanAgreement loanAgreement) {
        logger.info("Entered into updateLoanAgreement method");
        return loanAggRepo.save(loanAgreement);
    }
}
