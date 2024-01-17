package com.hla.in.homeloanapplication.service.impl;

import com.hla.in.homeloanapplication.entities.LoanAgreement;
import com.hla.in.homeloanapplication.exceptions.ResourceNotFoundException;
import com.hla.in.homeloanapplication.repository.ILoanAgreementRepository;
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
public class ILoanAgreementServiceImplTest {

    @Mock
    private ILoanAgreementRepository loanAggRepo;

    @InjectMocks
    private ILoanAgreementServiceImpl loanAgreementService;

    private LoanAgreement loanAgreement;

    @BeforeEach
    public void setUp() {
        loanAgreement = new LoanAgreement();
        loanAgreement.setLoanAgreementId(1L);
    }

    @Test
    public void testRetrieveAgreementByIdWhenValidIdThenReturnLoanAgreement() throws ResourceNotFoundException {
        when(loanAggRepo.findById(1L)).thenReturn(Optional.of(loanAgreement));

        LoanAgreement result = loanAgreementService.retrieveAgreementById(1L);

        assertEquals(loanAgreement, result);
    }

    @Test
    public void testRetrieveAgreementByIdWhenInvalidIdThenThrowException() {
        when(loanAggRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> loanAgreementService.retrieveAgreementById(1L));
    }

    @Test
    public void testAddLoanAgreementWhenValidLoanAgreementThenReturnLoanAgreement() {
        when(loanAggRepo.save(loanAgreement)).thenReturn(loanAgreement);

        LoanAgreement result = loanAgreementService.addLoanAgreement(loanAgreement);

        assertEquals(loanAgreement, result);
    }

    @Test
    public void testAddLoanAgreementWhenNullLoanAgreementThenThrowNullPointerException() {
        when(loanAggRepo.save(null)).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () -> loanAgreementService.addLoanAgreement(null));
    }
}