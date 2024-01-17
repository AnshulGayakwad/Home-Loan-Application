package com.hla.in.homeloanapplication.service;

import java.util.List;
import com.hla.in.homeloanapplication.dtos.CustomerDto;
import com.hla.in.homeloanapplication.dtos.DocsDto;
import com.hla.in.homeloanapplication.entities.Customer;
import com.hla.in.homeloanapplication.exceptions.CouldNotBeAddedException;
import com.hla.in.homeloanapplication.exceptions.ResourceNotFoundException;

public interface ICustomerService {

    Customer viewCustomer(int custid) throws ResourceNotFoundException;

    List<Customer> viewAllCustomers() throws ResourceNotFoundException;

    Customer addCustomer(CustomerDto customer) throws CouldNotBeAddedException;

    Customer updateCustomer(int id, DocsDto docsDto) throws ResourceNotFoundException;

    Customer deleteCustomerById(int custId) throws ResourceNotFoundException;

    List<Customer> viewCustomerList(String dateOfApplication) throws ResourceNotFoundException;

}
