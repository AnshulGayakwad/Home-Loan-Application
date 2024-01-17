package com.hla.in.homeloanapplication.repository;

import com.hla.in.homeloanapplication.entities.EMI;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEMIRepository extends JpaRepository<EMI,Long> {
}
