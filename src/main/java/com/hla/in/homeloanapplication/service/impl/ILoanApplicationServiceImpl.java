package com.hla.in.homeloanapplication.service.impl;


import com.hla.in.homeloanapplication.dtos.LoanApplicationDto;
import com.hla.in.homeloanapplication.entities.*;
import com.hla.in.homeloanapplication.enums.Status;
import com.hla.in.homeloanapplication.exceptions.CouldNotBeAddedException;
import com.hla.in.homeloanapplication.exceptions.ResourceNotFoundException;
import com.hla.in.homeloanapplication.repository.ICustomerRepository;
import com.hla.in.homeloanapplication.repository.IEMIRepository;
import com.hla.in.homeloanapplication.repository.ILoanApplicationRepository;
import com.hla.in.homeloanapplication.repository.ISchemeRepository;
import com.hla.in.homeloanapplication.service.ILoanApplicationService;
import com.hla.in.homeloanapplication.util.EMICalculator;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;


@Service
public class ILoanApplicationServiceImpl implements ILoanApplicationService {

    Log logger = LogFactory.getLog(ILoanApplicationServiceImpl.class);
    @Autowired
    ILoanApplicationRepository loanRepo;

    @Autowired
    private ICustomerRepository customerRepository;

    @Autowired
    private ISchemeRepository schemeRepository;

    @Autowired
    private IEMIRepository repository;


    String notFoundMessage = "No Loan Application found";

    @Override
    public LoanApplication deleteLoanApplicationId(long loanApplicationId) throws ResourceNotFoundException {
        logger.info("In deleteLoanApplicationById function in LoanApplicationServiceImpl");
        return loanRepo.findById(loanApplicationId)
                .orElseThrow(() -> new ResourceNotFoundException(notFoundMessage));

    }

    @Override
    public List<LoanApplication> retrieveAllLoanApplication() {
        logger.info("In retrieveAllLoanApplication function in LoanApplicationServiceImpl");
        /*
         * Finding all loan applications
         */
        return loanRepo.findAll();
    }

    @Override
    public LoanApplication retrieveLoanApplicationById(Long loanApplicationId) throws ResourceNotFoundException {
        logger.info("In retrieveLoanApplicationById function in LoanApplicationServiceImpl");
        /*
         * Finding loan applications by Id
         */
        return loanRepo.findById(loanApplicationId)
                .orElseThrow(() -> new ResourceNotFoundException(notFoundMessage));
    }

    @Override
    public LoanApplication addLoanApplication(@Valid LoanApplicationDto loanApplication) throws ResourceNotFoundException, CouldNotBeAddedException {
        logger.info("In addLoanApplication function in LoanApplicationServiceImpl");
        if (loanRepo.findByCustomerId(loanApplication.getCustomerId()) != null) {
            throw new CouldNotBeAddedException("Loan application exists with the customer ID : " + loanApplication.getCustomerId());
        }
        LoanApplication loanApplication1 = new LoanApplication();

        Customer customer = customerRepository.findById(loanApplication.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("No Customer found for the given loan"));

        loanApplication1.setLoanAppliedAmount(loanApplication.getLoanAppliedAmount());
        loanApplication1.setApplicationDate(loanApplication.getApplicationDate());
        loanApplication1.setTotalAnnualIncome(loanApplication.getTotalAnnualIncome());
        loanApplication1.setMonthlyExpenses(loanApplication.getMonthlyExpenses());
        loanApplication1.setOtherMonthlyExpenses(loanApplication.getOtherMonthlyExpenses());
        if (customer.getAadharNumber() == null || customer.getPanNumber() == null) {
            loanApplication1.setStatus(Status.DOCUMENTS_NOT_UPLOADED);
        } else {
            loanApplication1.setStatus(Status.DOCUMENTS_UPLOADED);
        }


        /*
        Find Customer by Id and save loan save it into Customer object
         */
        loanApplication1.setCustomer(customer);

        /*
        fetch scheme and append it to Loan application object
         */
        Scheme scheme = schemeRepository.findById(loanApplication.getSchemeId())
                .orElseThrow(() -> new ResourceNotFoundException("No Scheme Found For the given loan"));
        loanApplication1.setScheme(scheme);

        /*
        Find Customer and  save it into Loan object
         */
        return loanRepo.save(loanApplication1);
    }

    //updating loanApplication
    @Override
    public LoanApplication updateLoanApplication(long id) throws ResourceNotFoundException {
        logger.info("In updateLoanApplication function in LoanApplicationServiceImpl");

        LoanApplication loanApplication = loanRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(notFoundMessage));
        /*
         * If status is Documents uploaded then changing to LVO Approval.
         * if status is Pending then Approve Or Reject the loan
         * else throw exception
         */
        if (loanApplication.getStatus().equals(Status.DOCUMENTS_UPLOADED)) {
            loanApplication.setStatus(Status.valueOf(String.valueOf(Status.WAITING_FOR_LAND_VERIFICATION_OFFICE_APPROVAL)));
            return loanRepo.save(loanApplication);
        } else if (loanApplication.getStatus().equals(Status.PENDING)) {
            boolean verify = loanApplication.isLandVerificationApproval() && loanApplication.isFinanceVerificationApproval();
            loanApplication.setAdminApproval(verify);

            loanApplication.setStatus(verify ? Status.APPROVED : Status.REJECTED);

        } else {
            throw new ResourceNotFoundException("The application is still under processing stage");
        }


        EMI emi = new EMI();
        double approvedAmount = loanApplication.getLoanApprovedAmount();
        int tenure = loanApplication.getScheme().getTenure();
        LocalDate dueDate = loanApplication.getApplicationDate().plusYears(tenure);
        emi.setDueDate(dueDate); //calculate due date

        emi.setLoanAmount(approvedAmount);

        EMICalculator emiCalculator = new EMICalculator(approvedAmount, loanApplication.getScheme().getInterestRate(),tenure);

        emi.setEmiAmount(emiCalculator.getEMIAmount());

        double interestAmount = (emi.getEmiAmount() * tenure)
                - emi.getLoanAmount(); //find interest

        emi.setInterestAmount(Double.parseDouble(String.format("%.2f", interestAmount)));

            /*
            Saving EMI Object into Repo
             */
        repository.save(emi);
             /*
                 Making Loan Agreement with Customer  after loan is approved
             */
        LoanAgreement loanAgreement = new LoanAgreement();
        loanAgreement.setEmi(emi);

        loanApplication.setLoanAgreement(loanAgreement);


        return loanRepo.save(loanApplication);
    }

    @Override
    public LoanApplication updateStatusOfLoanApplication(Long loanApplicationId, Status status) throws ResourceNotFoundException {
        logger.info("In updateStatusOfLoanApplication function in LoanApplicationServiceImpl");
        LoanApplication application = loanRepo.findById(loanApplicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan Application not found"));
        if (application.getStatus().equals(Status.WAITING_FOR_LAND_VERIFICATION_OFFICE_APPROVAL)) {
            application.setStatus(Status.valueOf(String.valueOf(status)));
            application.setLandVerificationApproval(true);
            return loanRepo.save(application);
        } else {
            throw new ResourceNotFoundException("This application is not under your authority");
        }
    }

    @Override
    public List<LoanApplication> retrieveLoanApplicationByStatus(String status) {
        logger.info("In retrieveLoanApplicationByStatus function in LoanApplicationServiceImpl");
        Status penStatus;
        if(status.equals("WAITING_FOR_LAND_VERIFICATION_OFFICE_APPROVAL")){
            penStatus = Status.WAITING_FOR_LAND_VERIFICATION_OFFICE_APPROVAL;
        } else if (status.equals("PENDING")) {
            penStatus = Status.PENDING;
        }
        else if(status.equals("WAITING_FOR_FINANCE_APPROVAL")){
            penStatus= Status.WAITING_FOR_FINANCE_APPROVAL;
        }
        else if(status.equals("APPROVED")){
            penStatus = Status.APPROVED;
        }
        else if(status.equals("DOCUMENTS_UPLOADED")){
            penStatus = Status.DOCUMENTS_UPLOADED;
        }
        else {
            penStatus = Status.REJECTED;
        }
        return loanRepo.findByStatus(penStatus);
    }
}
