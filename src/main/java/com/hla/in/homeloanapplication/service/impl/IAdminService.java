package com.hla.in.homeloanapplication.service.impl;

import com.hla.in.homeloanapplication.dtos.AdminDto;
import com.hla.in.homeloanapplication.entities.Admin;
import com.hla.in.homeloanapplication.exceptions.CouldNotBeAddedException;
import com.hla.in.homeloanapplication.repository.IAdminRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IAdminService {
    @Autowired
    IAdminRepository adminRepository;
    public void addAdmin(AdminDto adminDto) throws CouldNotBeAddedException {
        Admin newAdmin = new Admin();
        if(adminDto == null) {
            throw new CouldNotBeAddedException("Something is Missing in Information");
        }
        BeanUtils.copyProperties(adminDto, newAdmin);
        newAdmin.setRole("admin");
        adminRepository.save(newAdmin);
    }
}
