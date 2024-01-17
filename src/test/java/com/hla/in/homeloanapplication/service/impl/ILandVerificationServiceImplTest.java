package com.hla.in.homeloanapplication.service.impl;

import com.hla.in.homeloanapplication.dtos.LandVerificationOfficerDto;
import com.hla.in.homeloanapplication.entities.LandVerificationOfficer;
import com.hla.in.homeloanapplication.entities.LoanApplication;
import com.hla.in.homeloanapplication.exceptions.CouldNotBeAddedException;
import com.hla.in.homeloanapplication.exceptions.ResourceNotFoundException;
import com.hla.in.homeloanapplication.repository.ILandVerificationRepository;
import com.hla.in.homeloanapplication.repository.ILoanApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ILandVerificationServiceImplTest {

    @Mock
    private ILandVerificationRepository landVerificationRepository;

    @Mock
    private ILoanApplicationRepository loanApplicationRepository;

    @InjectMocks
    private ILandVerificationServiceImpl landVerificationService;

    private LoanApplication loanApplication;
    private LandVerificationOfficerDto landVerificationOfficerDto;

    @BeforeEach
    public void setUp() {
        loanApplication = new LoanApplication();
        loanApplication.setApplicationId(1L);

        landVerificationOfficerDto = new LandVerificationOfficerDto();
        landVerificationOfficerDto.setOfficeContact("1234567890");
    }

    @Test
    public void testUpdateStatusWhenLoanApplicationIsFoundThenStatusIsUpdated() throws ResourceNotFoundException {
        when(loanApplicationRepository.findById(loanApplication.getApplicationId())).thenReturn(Optional.of(loanApplication));

        landVerificationService.updateStatus(loanApplication);

        verify(loanApplicationRepository, times(1)).save(loanApplication);
    }

    @Test
    public void testUpdateStatusWhenLoanApplicationIsNotFoundThenResourceNotFoundExceptionIsThrown() {
        when(loanApplicationRepository.findById(loanApplication.getApplicationId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> landVerificationService.updateStatus(loanApplication));
    }

    @Test
    public void testAddLandVerificationOfficerWhenOfficerDoesNotExistThenOfficerIsAdded() throws CouldNotBeAddedException {
        when(landVerificationRepository.findByOfficerContact(landVerificationOfficerDto.getOfficeContact())).thenReturn(null);

        LandVerificationOfficer landVerificationOfficer = new LandVerificationOfficer();
        when(landVerificationRepository.save(any(LandVerificationOfficer.class))).thenReturn(landVerificationOfficer);

        LandVerificationOfficer result = landVerificationService.addLandVerificationOfficer(landVerificationOfficerDto);

        verify(landVerificationRepository, times(1)).save(any(LandVerificationOfficer.class));
        assertSame(landVerificationOfficer, result);
    }

    @Test
    public void testAddLandVerificationOfficerWhenOfficerExistsThenCouldNotBeAddedExceptionIsThrown() {
        LandVerificationOfficer existingOfficer = new LandVerificationOfficer();
        when(landVerificationRepository.findByOfficerContact(landVerificationOfficerDto.getOfficeContact())).thenReturn(existingOfficer);

        assertThrows(CouldNotBeAddedException.class, () -> landVerificationService.addLandVerificationOfficer(landVerificationOfficerDto));
    }
}