package com.hla.in.homeloanapplication.repository;


import com.hla.in.homeloanapplication.entities.Scheme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISchemeRepository extends JpaRepository<Scheme,Integer>  {

}
