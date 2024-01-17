package com.hla.in.homeloanapplication.service.impl;

import com.hla.in.homeloanapplication.dtos.LoanApplicationDto;
import com.hla.in.homeloanapplication.entities.Customer;
import com.hla.in.homeloanapplication.entities.LoanApplication;
import com.hla.in.homeloanapplication.entities.Scheme;
import com.hla.in.homeloanapplication.enums.Status;
import com.hla.in.homeloanapplication.exceptions.CouldNotBeAddedException;
import com.hla.in.homeloanapplication.exceptions.ResourceNotFoundException;
import com.hla.in.homeloanapplication.repository.ICustomerRepository;
import com.hla.in.homeloanapplication.repository.IEMIRepository;
import com.hla.in.homeloanapplication.repository.ILoanApplicationRepository;
import com.hla.in.homeloanapplication.repository.ISchemeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ILoanApplicationServiceImplTest {

    @Mock
    private ILoanApplicationRepository loanRepo;

    @Mock
    private ICustomerRepository customerRepository;

    @Mock
    private ISchemeRepository schemeRepository;

    @Mock
    private IEMIRepository repository;

    @InjectMocks
    private ILoanApplicationServiceImpl loanApplicationService;

    private LoanApplication loanApplication;
    private LoanApplicationDto loanApplicationDto;
    private Customer customer;
    private Scheme scheme;

    @BeforeEach
    public void setUp() {
        loanApplication = new LoanApplication();
        loanApplication.setApplicationId(1L);
        loanApplication.setLoanAppliedAmount(50000);
        loanApplication.setApplicationDate(LocalDate.now());
        loanApplication.setTotalAnnualIncome(100000);
        loanApplication.setMonthlyExpenses(5000);
        loanApplication.setOtherMonthlyExpenses(2000);
        loanApplication.setStatus(Status.DOCUMENTS_UPLOADED);

        loanApplicationDto = new LoanApplicationDto();
        loanApplicationDto.setLoanAppliedAmount(50000);
        loanApplicationDto.setApplicationDate(LocalDate.now());
        loanApplicationDto.setTotalAnnualIncome(100000);
        loanApplicationDto.setMonthlyExpenses(5000);
        loanApplicationDto.setOtherMonthlyExpenses(2000);
        loanApplicationDto.setCustomerId(1);
        loanApplicationDto.setSchemeId(1);

        customer = new Customer();
        customer.setUserId(1);
        customer.setAadharNumber("123456789012");
        customer.setPanNumber("ABCDE1234F");

        scheme = new Scheme();
        scheme.setSchemeId(1);
    }

    // Existing tests...

    @Test
    public void testRetrieveAllLoanApplicationWhenLoanApplicationsExistThenReturnListOfLoanApplications() {
        when(loanRepo.findAll()).thenReturn(Arrays.asList(loanApplication));

        List<LoanApplication> loanApplications = loanApplicationService.retrieveAllLoanApplication();

        assertEquals(1, loanApplications.size());
        verify(loanRepo, times(1)).findAll();
    }

    @Test
    public void testRetrieveAllLoanApplicationWhenNoLoanApplicationsExistThenReturnEmptyList() {
        when(loanRepo.findAll()).thenReturn(Collections.emptyList());

        List<LoanApplication> loanApplications = loanApplicationService.retrieveAllLoanApplication();

        assertTrue(loanApplications.isEmpty());
        verify(loanRepo, times(1)).findAll();
    }


    @Test
    public void testDeleteLoanApplicationIdWhenLoanApplicationDoesNotExistThenThrowResourceNotFoundException() {
        when(loanRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            loanApplicationService.deleteLoanApplicationId(1L);
        });

        verify(loanRepo, times(1)).findById(1L);
    }

    @Test
    public void testRetrieveAllLoanApplication() {
        when(loanRepo.findAll()).thenReturn(Arrays.asList(loanApplication));

        List<LoanApplication> loanApplications = loanApplicationService.retrieveAllLoanApplication();

        assertEquals(1, loanApplications.size());
        verify(loanRepo, times(1)).findAll();
    }

    @Test
    public void testRetrieveLoanApplicationByIdWhenLoanApplicationExistsThenReturnLoanApplication() throws ResourceNotFoundException {
        when(loanRepo.findById(1L)).thenReturn(Optional.of(loanApplication));

        LoanApplication result = loanApplicationService.retrieveLoanApplicationById(1L);

        assertEquals(loanApplication, result);
        verify(loanRepo, times(1)).findById(1L);
    }

    @Test
    public void testRetrieveLoanApplicationByIdWhenLoanApplicationDoesNotExistThenThrowResourceNotFoundException() {
        when(loanRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            loanApplicationService.retrieveLoanApplicationById(1L);
        });

        verify(loanRepo, times(1)).findById(1L);
    }

    @Test
    public void testAddLoanApplicationWhenCustomerExistsAndLoanApplicationDoesNotExistThenAddLoanApplication() throws ResourceNotFoundException, CouldNotBeAddedException {
        when(loanRepo.findByCustomerId(1)).thenReturn(null);
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(schemeRepository.findById(1)).thenReturn(Optional.of(scheme));
        when(loanRepo.save(any(LoanApplication.class))).thenReturn(loanApplication);

        LoanApplication result = loanApplicationService.addLoanApplication(loanApplicationDto);

        assertEquals(loanApplication, result);
        verify(loanRepo, times(1)).findByCustomerId(1);
        verify(customerRepository, times(1)).findById(1);
        verify(schemeRepository, times(1)).findById(1);
        verify(loanRepo, times(1)).save(any(LoanApplication.class));
    }

    @Test
    public void testAddLoanApplicationWhenLoanApplicationExistsThenThrowCouldNotBeAddedException() {
        when(loanRepo.findByCustomerId(1)).thenReturn(loanApplication);

        assertThrows(CouldNotBeAddedException.class, () -> {
            loanApplicationService.addLoanApplication(loanApplicationDto);
        });

        verify(loanRepo, times(1)).findByCustomerId(1);
    }

    @Test
    public void testUpdateLoanApplicationWhenLoanApplicationExistsAndStatusIsDocumentsUploadedThenUpdateLoanApplication() throws ResourceNotFoundException {
        when(loanRepo.findById(1L)).thenReturn(Optional.of(loanApplication));
        when(loanRepo.save(any(LoanApplication.class))).thenReturn(loanApplication);

        LoanApplication result = loanApplicationService.updateLoanApplication(1L);

        assertEquals(loanApplication, result);
        verify(loanRepo, times(1)).findById(1L);
        verify(loanRepo, times(1)).save(any(LoanApplication.class));
    }

    @Test
    public void testUpdateLoanApplicationWhenLoanApplicationDoesNotExistThenThrowResourceNotFoundException() {
        when(loanRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            loanApplicationService.updateLoanApplication(1L);
        });

        verify(loanRepo, times(1)).findById(1L);
    }

    @Test
    public void testUpdateStatusOfLoanApplicationWhenLoanApplicationExistsAndStatusIsWaitingForLandVerificationOfficeApprovalThenUpdateStatusOfLoanApplication() throws ResourceNotFoundException {
        loanApplication.setStatus(Status.WAITING_FOR_LAND_VERIFICATION_OFFICE_APPROVAL);
        when(loanRepo.findById(1L)).thenReturn(Optional.of(loanApplication));
        when(loanRepo.save(any(LoanApplication.class))).thenReturn(loanApplication);

        LoanApplication result = loanApplicationService.updateStatusOfLoanApplication(1L, Status.APPROVED);

        assertEquals(loanApplication, result);
        verify(loanRepo, times(1)).findById(1L);
        verify(loanRepo, times(1)).save(any(LoanApplication.class));
    }

    @Test
    public void testUpdateStatusOfLoanApplicationWhenLoanApplicationDoesNotExistThenThrowResourceNotFoundException() {
        when(loanRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            loanApplicationService.updateStatusOfLoanApplication(1L, Status.APPROVED);
        });

        verify(loanRepo, times(1)).findById(1L);
    }

    @Test
    public void testRetrieveLoanApplicationByStatusWhenLoanApplicationsExistThenReturnLoanApplications() {
        when(loanRepo.findByStatus(Status.APPROVED)).thenReturn(Arrays.asList(loanApplication));

        List<LoanApplication> loanApplications = loanApplicationService.retrieveLoanApplicationByStatus("APPROVED");

        assertEquals(1, loanApplications.size());
        verify(loanRepo, times(1)).findByStatus(Status.APPROVED);
    }
}