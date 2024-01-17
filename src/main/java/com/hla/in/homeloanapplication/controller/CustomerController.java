package com.hla.in.homeloanapplication.controller;

import com.hla.in.homeloanapplication.dtos.CustomerDto;
import com.hla.in.homeloanapplication.dtos.DocsDto;
import com.hla.in.homeloanapplication.dtos.LoanApplicationDto;
import com.hla.in.homeloanapplication.entities.Customer;
import com.hla.in.homeloanapplication.entities.LoanAgreement;
import com.hla.in.homeloanapplication.entities.LoanApplication;
import com.hla.in.homeloanapplication.entities.Scheme;
import com.hla.in.homeloanapplication.exceptions.CouldNotBeAddedException;
import com.hla.in.homeloanapplication.exceptions.ResourceNotFoundException;
import com.hla.in.homeloanapplication.service.impl.ICustomerServiceImpl;
import com.hla.in.homeloanapplication.service.impl.ILoanAgreementServiceImpl;
import com.hla.in.homeloanapplication.service.impl.ILoanApplicationServiceImpl;
import com.hla.in.homeloanapplication.service.impl.ISchemeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private ILoanApplicationServiceImpl loanApplicationService;

    @Autowired
    private ICustomerServiceImpl customerService;

    @Autowired
    private ISchemeServiceImpl iSchemeService;

    @Autowired
    private ILoanAgreementServiceImpl iLoanAgreementService;


    //Only Customer can be Apply to New Loan Application
    @PostMapping("/apply")
    @Operation(summary = "Create New Loan Application", description = "Provide Date in dd/MM/yyyy format")
    public ResponseEntity<LoanApplication> createNewLoanApplication(@Valid @RequestBody LoanApplicationDto loanApplicationDto) throws ResourceNotFoundException, CouldNotBeAddedException {

        loanApplicationDto.setApplicationDate(LocalDate.now());
        LoanApplication savedLoanApplication = loanApplicationService.addLoanApplication(loanApplicationDto);
        return new ResponseEntity<>(savedLoanApplication, HttpStatus.CREATED);

    }

    //Adding New Customer Using CustomerDTO
    @PostMapping("/addCustomer")
    @Operation(summary = "Create New Customer", description = "Provide Date in dd/MM/yyyy format")
    public ResponseEntity<Customer> createNewCustomer(@Valid @RequestBody CustomerDto customerDto) throws CouldNotBeAddedException {

        Customer newCustomer = customerService.addCustomer(customerDto);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }


    @PutMapping("/document/{id}")
    @Operation(summary = "Update Documents", description = "")
    public ResponseEntity<Customer> updateDocuments(@PathVariable int id, @Valid @RequestBody DocsDto docsDto) throws ResourceNotFoundException {
        Customer customer = customerService.updateCustomer(id, docsDto);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    @GetMapping("/loan/{loanApplicationId}")
    @Operation(summary = "Get Loan Application by ID", description = "")
    public ResponseEntity<LoanApplication> retrieveLoanApplicationById(@PathVariable Long loanApplicationId) throws ResourceNotFoundException {
        LoanApplication loanApplication;
        loanApplication = loanApplicationService.retrieveLoanApplicationById(loanApplicationId);
        return new ResponseEntity<>(loanApplication, HttpStatus.OK);
    }

    @GetMapping("/schemes")
    @Operation(summary = "Get All Schemes", description = "")
    public ResponseEntity<List<Scheme>> getAllSchemes() {

        List<Scheme> schemesList = iSchemeService.getAllSchemes();
        return new ResponseEntity<>(schemesList, HttpStatus.OK);
    }

    @GetMapping("/loanagreement/{id}")
    @Operation(summary = "Get Loan Agreement by ID / View Agreement Status by Agreement ID", description = "")
    public ResponseEntity<LoanAgreement> retrieveLoanAgreementById(@PathVariable Long id) throws ResourceNotFoundException {
        LoanAgreement loanAgreement = iLoanAgreementService.retrieveAgreementById(id);
        return new ResponseEntity<>(loanAgreement, HttpStatus.OK);
    }


}
