package com.hla.in.homeloanapplication.service.impl;

import com.hla.in.homeloanapplication.dtos.AdminDto;
import com.hla.in.homeloanapplication.entities.Admin;
import com.hla.in.homeloanapplication.exceptions.CouldNotBeAddedException;
import com.hla.in.homeloanapplication.repository.IAdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class IAdminServiceTest {

    @Mock
    private IAdminRepository adminRepository;

    @InjectMocks
    private IAdminService adminService;

    private AdminDto adminDto;
    private Admin admin;

    @BeforeEach
    public void setUp() {
        adminDto = new AdminDto();
        adminDto.setUserId(1);
        adminDto.setPassword("password");
        adminDto.setAdminName("admin");
        adminDto.setAdminContact("1234567890");

        admin = new Admin();
        BeanUtils.copyProperties(adminDto, admin);
        admin.setRole("admin");
    }

    @Test
    public void testAddAdminWhenAdminDtoIsNullThenThrowCouldNotBeAddedException() {
        assertThrows(CouldNotBeAddedException.class, () -> {
            adminService.addAdmin(null);
        });
    }
}