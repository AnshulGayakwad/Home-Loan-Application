package com.hla.in.homeloanapplication.service.impl;


import com.hla.in.homeloanapplication.dtos.CustomerDto;
import com.hla.in.homeloanapplication.dtos.DocsDto;
import com.hla.in.homeloanapplication.entities.Customer;
import com.hla.in.homeloanapplication.entities.LoanApplication;
import com.hla.in.homeloanapplication.enums.Status;
import com.hla.in.homeloanapplication.exceptions.CouldNotBeAddedException;
import com.hla.in.homeloanapplication.exceptions.ResourceNotFoundException;
import com.hla.in.homeloanapplication.repository.ICustomerRepository;
import com.hla.in.homeloanapplication.repository.ILoanApplicationRepository;
import com.hla.in.homeloanapplication.service.ICustomerService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ICustomerServiceImpl implements ICustomerService {
    Log logger = LogFactory.getLog(ILoanAgreementServiceImpl.class);
    @Autowired
    ICustomerRepository custRepo;

    @Autowired
    ILoanApplicationRepository loanRepo;

    @Override
    public Customer viewCustomer(int custId) throws ResourceNotFoundException {
        logger.info("Entered into viewCustomer method");
        return custRepo.findById(custId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not found for Id " + custId));
    }

    @Override
    public List<Customer> viewAllCustomers() throws ResourceNotFoundException {
        logger.info("Entered into viewAllCustomers method");
        if (custRepo.findAll().isEmpty()) {
            throw new ResourceNotFoundException("No Users Found");
        }
        return custRepo.findAll();
    }

    @Override
    public Customer addCustomer(CustomerDto customerDto) throws CouldNotBeAddedException {
        logger.info("Entered into addCustomer method");
        if (custRepo.findByMobileNumber(customerDto.getMobileNumber()) != null) {
            throw new CouldNotBeAddedException("Customer already exists With Mobile Number");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        Customer newCustomer = new Customer();
        BeanUtils.copyProperties(customerDto, newCustomer);
        newCustomer.setDateOfBirth(LocalDate.parse(customerDto.getDateOfBirth(), formatter));
        newCustomer.setRole("customer");
        return custRepo.save(newCustomer);
    }

    @Override
    public Customer updateCustomer(int id, DocsDto docsDto) throws ResourceNotFoundException {
        logger.info("Entered into updateCustomer method");
        Optional<Customer> customerOp = custRepo.findById(id);
        if (customerOp.isPresent()) {
            Customer customer = customerOp.get();
            customer.setPanNumber(docsDto.getPanNumber());
            customer.setAadharNumber(docsDto.getAadharNumber());
            LoanApplication loanApplication = loanRepo.findByCustomerId(id);
            loanApplication.setStatus(Status.DOCUMENTS_UPLOADED);
            loanRepo.save(loanApplication);
            return custRepo.save(customer);
        } else {
            throw new ResourceNotFoundException("User Not found for Id : " + id);
        }
    }

    @Override
    public List<Customer> viewCustomerList(String date) throws ResourceNotFoundException {
        logger.info("Entered into viewCustomerList method");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfApplication = LocalDate.parse(date, formatter);
        List<Customer> customers = custRepo.findByDateOfApplication(dateOfApplication);
        if (customers.isEmpty()) {
            throw new ResourceNotFoundException("No users found for the given date");
        }
        return customers;
    }

    @Override
    public Customer deleteCustomerById(int custId) throws ResourceNotFoundException {
        logger.info("Entered into deleteCustomerById method");
        Optional<Customer> customerOp = custRepo.findById(custId);
        if (customerOp.isPresent()) {
            custRepo.deleteById(custId);
            return customerOp.get();
        } else {
            throw new ResourceNotFoundException("User Not found for Id" + custId);
        }
    }
}
