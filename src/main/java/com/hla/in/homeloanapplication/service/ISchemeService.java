package com.hla.in.homeloanapplication.service;



import com.hla.in.homeloanapplication.dtos.SchemeDto;
import com.hla.in.homeloanapplication.entities.Scheme;
import com.hla.in.homeloanapplication.exceptions.InvalidCredentialException;
import com.hla.in.homeloanapplication.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ISchemeService {
    List<Scheme> getAllSchemes();
    Scheme getSchemeById(int schemeid) throws ResourceNotFoundException;
    Scheme addScheme(SchemeDto schemeDto);
    Scheme deleteSchemeById(int id) throws ResourceNotFoundException;
    Scheme updateScheme(int id,SchemeDto schemeDto) throws ResourceNotFoundException;
}
