package com.hla.in.homeloanapplication.repository;

import com.hla.in.homeloanapplication.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hla.in.homeloanapplication.entities.LoanApplication;

import java.util.List;

@Repository
public interface ILoanApplicationRepository extends JpaRepository<LoanApplication, Long>{
    @Query("select l from LoanApplication l, Customer c where c.userId = l.customer.userId and c.userId = ?1")
    LoanApplication findByCustomerId(int id);

    List<LoanApplication> findByStatus(Status status);
    List<LoanApplication> findByStatus(String status);
}
