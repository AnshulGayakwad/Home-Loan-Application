package com.hla.in.homeloanapplication.repository;

import com.hla.in.homeloanapplication.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAdminRepository extends JpaRepository<Admin, Integer> {
}
