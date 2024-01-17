package com.hla.in.homeloanapplication.service.impl;

import com.hla.in.homeloanapplication.dtos.CustomerDto;
import com.hla.in.homeloanapplication.entities.Customer;
import com.hla.in.homeloanapplication.exceptions.CouldNotBeAddedException;
import com.hla.in.homeloanapplication.exceptions.ResourceNotFoundException;
import com.hla.in.homeloanapplication.repository.ICustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ICustomerServiceImplTest {

    @Mock
    private ICustomerRepository customerRepository;

    @InjectMocks
    private ICustomerServiceImpl customerService;

    private Customer customer1;
    private Customer customer2;
    private CustomerDto customerDto;

    @BeforeEach
    public void setUp() {
        customer1 = new Customer();
        customer1.setCustomerName("John Doe");
        customer2 = new Customer();
        customer2.setCustomerName("Jane Doe");
        customerDto = new CustomerDto();
        customerDto.setCustomerName("New Customer");
        customerDto.setMobileNumber("1234567890");
    }

    @Test
    public void testViewAllCustomersWhenCustomersExistThenReturnCustomers() throws ResourceNotFoundException {
        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));
        List<Customer> customers = customerService.viewAllCustomers();
        assertFalse(customers.isEmpty());
        assertEquals(2, customers.size());
    }

    @Test
    public void testViewAllCustomersWhenNoCustomersThenThrowException() {
        when(customerRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(ResourceNotFoundException.class, () -> customerService.viewAllCustomers());
    }

    @Test
    public void testAddCustomerWhenExistingCustomerThenThrowException() {
        when(customerRepository.findByMobileNumber(customerDto.getMobileNumber())).thenReturn(customer1);
        assertThrows(CouldNotBeAddedException.class, () -> customerService.addCustomer(customerDto));
    }

    @Test
    public void testDeleteCustomerByIdWhenCustomerExistsThenDeleteCustomer() throws ResourceNotFoundException {
        int id = 1;
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer1));
        Customer deletedCustomer = customerService.deleteCustomerById(id);
        verify(customerRepository, times(1)).deleteById(id);
        assertEquals(customer1, deletedCustomer);
    }

    @Test
    public void testDeleteCustomerByIdWhenCustomerDoesNotExistThenThrowException() {
        int id = 1;
        when(customerRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> customerService.deleteCustomerById(id));
        verify(customerRepository, never()).deleteById(id);
    }
}