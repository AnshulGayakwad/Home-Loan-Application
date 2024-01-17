package com.hla.in.homeloanapplication.repository;

import com.hla.in.homeloanapplication.entities.FinanceVerificationOfficer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hla.in.homeloanapplication.entities.LoanApplication;

@Repository
public interface IFinanceVerificationRepository extends JpaRepository<FinanceVerificationOfficer, Integer> {
    FinanceVerificationOfficer findByFinOfficerContact(String finOfficerContact);
}
