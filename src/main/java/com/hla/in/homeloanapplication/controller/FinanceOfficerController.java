package com.hla.in.homeloanapplication.controller;


import com.hla.in.homeloanapplication.dtos.FinanceVerificationOfficerDto;
import com.hla.in.homeloanapplication.entities.FinanceVerificationOfficer;
import com.hla.in.homeloanapplication.entities.LoanApplication;
import com.hla.in.homeloanapplication.enums.Status;
import com.hla.in.homeloanapplication.exceptions.CouldNotBeAddedException;
import com.hla.in.homeloanapplication.exceptions.ResourceNotFoundException;
import com.hla.in.homeloanapplication.service.IFinanceVerificationService;
import com.hla.in.homeloanapplication.service.ILoanApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/financeofficer")
public class FinanceOfficerController {


    @Autowired
    ILoanApplicationService loanApplicationService;

    @Autowired
    IFinanceVerificationService financeVerificationService;


    @PostMapping("/addNewFinanceOfficer")
    @Operation(summary = "Create new Finance Officer", description = "")
    public ResponseEntity<FinanceVerificationOfficer> createNewFinanceVerificationOfficer(@Valid @RequestBody FinanceVerificationOfficerDto financeOfficerDto) throws CouldNotBeAddedException {
        FinanceVerificationOfficer newFinanceVerificationOfficer = financeVerificationService.addFinanceVerificationOfficer(financeOfficerDto);
        return new ResponseEntity<>(newFinanceVerificationOfficer, HttpStatus.CREATED);
    }

    @GetMapping("/loan/{id}")
    @Operation(summary = "Get Loan Application by ID", description = "")
    public ResponseEntity<LoanApplication> retrieveLoanApplicationById(@PathVariable Long id) throws ResourceNotFoundException
    {
        LoanApplication loanApplication = loanApplicationService.retrieveLoanApplicationById(id);
        return new ResponseEntity<>(loanApplication, HttpStatus.OK);
    }

    @PutMapping("/loan/{id}")
    @Operation(summary = "Update/Approve Status of Loan Application by ID", description = "")
    public ResponseEntity<LoanApplication> updateStatusOfLoanApplication(@PathVariable Long id) throws ResourceNotFoundException
    {
        LoanApplication loanApplication = financeVerificationService.updateStatus(id);
        return new ResponseEntity<>(loanApplication, HttpStatus.OK);
    }

    @GetMapping("/loans/pending")
    @Operation(summary = "Get All Pending Applications", description = "")
    public ResponseEntity<List<LoanApplication>> getPendingApplications() {
        List<LoanApplication> pendingApplications = loanApplicationService.retrieveLoanApplicationByStatus(
                String.valueOf(Status.WAITING_FOR_FINANCE_APPROVAL)
        );
        return new ResponseEntity<>(pendingApplications, HttpStatus.OK);
    }
}
