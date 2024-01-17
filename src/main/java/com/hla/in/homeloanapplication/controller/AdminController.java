package com.hla.in.homeloanapplication.controller;


import com.hla.in.homeloanapplication.dtos.AdminDto;
import com.hla.in.homeloanapplication.dtos.SchemeDto;
import com.hla.in.homeloanapplication.entities.*;
import com.hla.in.homeloanapplication.enums.Status;
import com.hla.in.homeloanapplication.exceptions.CouldNotBeAddedException;
import com.hla.in.homeloanapplication.exceptions.ResourceNotFoundException;
import com.hla.in.homeloanapplication.service.ICustomerService;
import com.hla.in.homeloanapplication.service.ILoanApplicationService;
import com.hla.in.homeloanapplication.service.ISchemeService;
import com.hla.in.homeloanapplication.service.impl.IAdminService;
import com.hla.in.homeloanapplication.service.impl.ILoanAgreementServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    ICustomerService customerService;

    @Autowired
    ILoanApplicationService loanApplicationService;

    @Autowired
    ISchemeService schemeService;

    @Autowired
    ILoanAgreementServiceImpl iLoanAgreementService;

    @Autowired
    IAdminService iAdminService;

    @PostMapping("/addNewAdmin")
    @Operation(summary = "Add new Admin", description = "")
    public ResponseEntity addNewAdmin(@Valid @RequestBody AdminDto adminDto) throws CouldNotBeAddedException {
        iAdminService.addAdmin(adminDto);
        return new ResponseEntity("New Admin Added", HttpStatus.OK);
    }

    @GetMapping("/users")
    @Operation(summary = "Get All Customers", description = "")
    public ResponseEntity<List<Customer>> getUsers() throws ResourceNotFoundException {
        List<Customer> customers = customerService.viewAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/users/{date}")
    @Operation(summary = "View Customer List By Date", description = "On the Basic of Date -> Input Date in 'yyyy-MM-dd' format")
    public ResponseEntity<List<Customer>> viewCustomerList(@PathVariable String date) throws ResourceNotFoundException{
        List<Customer> customers = customerService.viewCustomerList(date);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "View Customer by ID", description = "")
    public ResponseEntity<Customer> viewCustomer(@PathVariable int id) throws ResourceNotFoundException{
        Customer customer = customerService.viewCustomer(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    @Operation(summary = "Delete Customer by ID", description = "")
    public ResponseEntity<Customer> deleteCustomerById(@PathVariable int id) throws ResourceNotFoundException{
        Customer customer = customerService.deleteCustomerById(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/applications")
    @Operation(summary = "Get all Loan Application", description = "")
    public ResponseEntity<List<LoanApplication>> retrieveAllLoanApplication() {
        List<LoanApplication> applications = loanApplicationService.retrieveAllLoanApplication();
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    @GetMapping("/application/{id}")
    @Operation(summary = "Get Loan Application by ID", description = "")
    public ResponseEntity<LoanApplication> getApplicationById(@PathVariable long id) throws ResourceNotFoundException {
        LoanApplication loanApplication = loanApplicationService.retrieveLoanApplicationById(id);
        return new ResponseEntity<>(loanApplication, HttpStatus.OK);
    }

    @PutMapping("/application/{id}")
    @Operation(summary = "Update Loan Application by ID", description = "")
    public ResponseEntity<LoanApplication> updateApplicationById(@PathVariable long id) throws ResourceNotFoundException{
        LoanApplication  loanApplication = loanApplicationService.updateLoanApplication(id);
        return new ResponseEntity<>( loanApplication, HttpStatus.OK);
    }

    @GetMapping("/application/pending")
    @Operation(summary = "Get all Pending Applications", description = "")
    public ResponseEntity<List<LoanApplication>> getPendingApplications(){
        List<LoanApplication> pendingApplications = loanApplicationService.retrieveLoanApplicationByStatus("PENDING");
        return new ResponseEntity<>(pendingApplications, HttpStatus.OK);
    }

    @GetMapping("/applications/documentsuploaded")
    @Operation(summary = "Get Loan Application with Uploaded Docs", description = "")
    public ResponseEntity<List<LoanApplication>> getDocumentUploaded(){
        List<LoanApplication> pendingApplications = loanApplicationService.retrieveLoanApplicationByStatus(String.valueOf(Status.DOCUMENTS_UPLOADED));
        return new ResponseEntity<>(pendingApplications, HttpStatus.OK);
    }

    @PutMapping("application/document/{id}")
    @Operation(summary = "Raise New Ticket By Loan Application ID", description = "")
    public ResponseEntity<LoanApplication> raiseLandOfficerTicket(@PathVariable long id) throws ResourceNotFoundException{
        LoanApplication  savedLoanApplication = loanApplicationService.updateLoanApplication(id);
        return new ResponseEntity<>( savedLoanApplication, HttpStatus.OK);
    }


    @PostMapping("/scheme")
    @Operation(summary = "Add new Scheme", description = "")
    public ResponseEntity<Scheme> addScheme(@Valid @RequestBody SchemeDto schemeDto){
        Scheme scheme = schemeService.addScheme(schemeDto);
        return new ResponseEntity<>(scheme,HttpStatus.OK);
    }

    @GetMapping("/scheme")
    @Operation(summary = "Get All Schemes", description = "")
    public ResponseEntity<List<Scheme>> getAllSchemes(){
        List<Scheme> schemeList = schemeService.getAllSchemes();
        return new ResponseEntity<>(schemeList,HttpStatus.OK);
    }
    @GetMapping("/scheme/{id}")
    @Operation(summary = "Get Scheme by ID", description = "")
    public ResponseEntity<Scheme> getSchemeById(@PathVariable int id) throws ResourceNotFoundException{
        Scheme scheme = schemeService.getSchemeById(id);
        return new ResponseEntity<>(scheme,HttpStatus.OK);
    }

    @DeleteMapping("/scheme/{id}")
    @Operation(summary = "Delete Scheme by ID", description = "")
    public ResponseEntity<Scheme> deleteSchemeById(@PathVariable int id) throws ResourceNotFoundException {
        Scheme scheme = schemeService.deleteSchemeById(id);
        return new ResponseEntity<>(scheme, HttpStatus.OK);
    }
    @PutMapping("/scheme/{id}")
    @Operation(summary = "Update Scheme by ID", description = "")
    public ResponseEntity<Scheme> updateScheme(@PathVariable int id,@RequestBody SchemeDto schemeDto) throws ResourceNotFoundException {
        Scheme scheme1 = schemeService.updateScheme(id,schemeDto);
        return new ResponseEntity<>(scheme1, HttpStatus.OK);
    }

    @GetMapping("/loanagreement/{id}")
    @Operation(summary = "Get Loan Agreement by ID", description = "")
    public ResponseEntity<LoanAgreement> retrieveLoanAgreementById(@PathVariable Long id) throws ResourceNotFoundException
    {
        LoanAgreement loanAgreement = iLoanAgreementService.retrieveAgreementById(id);
        return new ResponseEntity<>(loanAgreement,HttpStatus.OK);
    }

}
