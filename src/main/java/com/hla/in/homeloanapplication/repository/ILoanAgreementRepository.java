package com.hla.in.homeloanapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hla.in.homeloanapplication.entities.LoanAgreement;


@Repository
public interface ILoanAgreementRepository extends JpaRepository<LoanAgreement, Long>{

}
