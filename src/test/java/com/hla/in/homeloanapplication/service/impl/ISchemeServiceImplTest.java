package com.hla.in.homeloanapplication.service.impl;

import com.hla.in.homeloanapplication.dtos.SchemeDto;
import com.hla.in.homeloanapplication.entities.Scheme;
import com.hla.in.homeloanapplication.exceptions.ResourceNotFoundException;
import com.hla.in.homeloanapplication.repository.ISchemeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ISchemeServiceImplTest {

    @Mock
    private ISchemeRepository schemeRepo;

    @InjectMocks
    private ISchemeServiceImpl schemeService;

    private Scheme scheme;
    private SchemeDto schemeDto;

    @BeforeEach
    public void setUp() {
        scheme = new Scheme();
        scheme.setSchemeId(1);
        scheme.setSchemeName("Test Scheme");
        scheme.setInterestRate(5.0);
        scheme.setTenure(10);

        schemeDto = new SchemeDto();
        schemeDto.setSchemeName("Updated Scheme");
        schemeDto.setInterestRate(6.0);
        schemeDto.setTenure(12);
    }

    @Test
    public void testGetSchemeByIdWhenValidIdThenReturnScheme() throws ResourceNotFoundException {
        when(schemeRepo.findById(1)).thenReturn(Optional.of(scheme));

        Scheme result = schemeService.getSchemeById(1);

        assertEquals(scheme, result);
    }

    @Test
    public void testGetSchemeByIdWhenInvalidIdThenThrowException() {
        when(schemeRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> schemeService.getSchemeById(1));
    }

    @Test
    public void testUpdateSchemeWhenSchemeIsUpdatedThenReturnUpdatedScheme() throws ResourceNotFoundException {
        when(schemeRepo.findById(1)).thenReturn(Optional.of(scheme));
        when(schemeRepo.save(scheme)).thenReturn(scheme);

        Scheme result = schemeService.updateScheme(1, schemeDto);

        assertEquals(scheme, result);
        assertEquals(schemeDto.getInterestRate(), result.getInterestRate());
        assertEquals(schemeDto.getTenure(), result.getTenure());
    }

    @Test
    public void testUpdateSchemeWhenSchemeIsNotFoundThenThrowException() {
        when(schemeRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> schemeService.updateScheme(1, schemeDto));
    }
}