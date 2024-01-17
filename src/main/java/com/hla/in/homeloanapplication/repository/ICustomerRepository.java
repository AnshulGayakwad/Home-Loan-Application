package com.hla.in.homeloanapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hla.in.homeloanapplication.entities.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("select c from Customer c where userId in ( select l.customer.userId from LoanApplication l " +
            "where l.applicationDate = ?1)")
    List<Customer> findByDateOfApplication(LocalDate dateOfApplication);

    Customer findByMobileNumber(String mobileNumber);
}

