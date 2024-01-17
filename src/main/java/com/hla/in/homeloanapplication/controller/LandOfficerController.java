package com.hla.in.homeloanapplication.controller;

import com.hla.in.homeloanapplication.dtos.LandVerificationOfficerDto;
import com.hla.in.homeloanapplication.entities.LandVerificationOfficer;
import com.hla.in.homeloanapplication.entities.LoanApplication;
import com.hla.in.homeloanapplication.enums.Status;
import com.hla.in.homeloanapplication.exceptions.CouldNotBeAddedException;
import com.hla.in.homeloanapplication.exceptions.InvalidCredentialException;
import com.hla.in.homeloanapplication.exceptions.ResourceNotFoundException;
import com.hla.in.homeloanapplication.service.ILandVerificationService;
import com.hla.in.homeloanapplication.service.ILoanApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/landofficer")
public class LandOfficerController {

    @Autowired
    ILoanApplicationService loanApplicationService;

    @Autowired
    ILandVerificationService landVerificationService;

    @PostMapping("/addNewLandOfficer")
    @Operation(summary = "Create new Land Verification Officer", description = "")
    public ResponseEntity<LandVerificationOfficer> createNewCustomer(@Valid @RequestBody LandVerificationOfficerDto landVerificationOfficerDto) throws CouldNotBeAddedException {
        LandVerificationOfficer newLandOfficer = landVerificationService.addLandVerificationOfficer(landVerificationOfficerDto);
        return new ResponseEntity<>(newLandOfficer, HttpStatus.CREATED);
    }

    @GetMapping("/loan/{id}")
    @Operation(summary = "Get Loan Application by ID", description = "")
    public ResponseEntity<LoanApplication> retrieveLoanApplicationById(@PathVariable Long id) throws ResourceNotFoundException {
        LoanApplication loanApplication = loanApplicationService.retrieveLoanApplicationById(id);
        return new ResponseEntity<>(loanApplication, HttpStatus.OK);
    }

    @PutMapping("/loan/{id}")
    @Operation(summary = "Update/Approve Status of Application by ID", description = "")
    public ResponseEntity<LoanApplication> updateStatusOfLoanApplication(@PathVariable Long id) throws ResourceNotFoundException {
        LoanApplication loanApplication = loanApplicationService.updateStatusOfLoanApplication(id, Status.WAITING_FOR_FINANCE_APPROVAL);
        return new ResponseEntity<>(loanApplication, HttpStatus.OK);
    }

    @GetMapping("/loans/pending")
    @Operation(summary = "Get All Pending Applications", description = "")
    public ResponseEntity<List<LoanApplication>> getPendingApplications() throws ResourceNotFoundException {
        String str = "WAITING_FOR_LAND_VERIFICATION_OFFICE_APPROVAL";
        List<LoanApplication> pendingApplications = loanApplicationService.retrieveLoanApplicationByStatus(str);
        if(pendingApplications == null) {
            throw new ResourceNotFoundException("List is Empty");
        }
        return new ResponseEntity<>(pendingApplications, HttpStatus.OK);
    }
}

