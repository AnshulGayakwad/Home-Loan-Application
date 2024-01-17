package com.hla.in.homeloanapplication.service.impl;

import com.hla.in.homeloanapplication.dtos.LandVerificationOfficerDto;
import com.hla.in.homeloanapplication.entities.LandVerificationOfficer;
import com.hla.in.homeloanapplication.entities.LoanApplication;
import com.hla.in.homeloanapplication.exceptions.CouldNotBeAddedException;
import com.hla.in.homeloanapplication.exceptions.ResourceNotFoundException;
import com.hla.in.homeloanapplication.repository.ILandVerificationRepository;
import com.hla.in.homeloanapplication.repository.ILoanApplicationRepository;
import com.hla.in.homeloanapplication.service.ILandVerificationService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ILandVerificationServiceImpl implements ILandVerificationService {

    Log logger = LogFactory.getLog(ILoanAgreementServiceImpl.class);
    @Autowired
    ILandVerificationRepository landVerificationRepository;

    @Autowired
    ILoanApplicationRepository loanApplicationRepository;

    @Override
    public void updateStatus(LoanApplication loanApplication) throws ResourceNotFoundException {
        //Updating/Approving the status of the Raised Ticket by Admin
        logger.info("Entered into updateStatus method in LandVerification Class");
        if(loanApplicationRepository.findById(loanApplication.getApplicationId()).isPresent()){
            loanApplication.setLandVerificationApproval(true);
            loanApplicationRepository.save(loanApplication);
        }else{
            throw new ResourceNotFoundException("Application Not Found" + loanApplication);
        }
        logger.info("Exited from updateStatus method in LandVerification Class");

    }

    @Override
    public LandVerificationOfficer addLandVerificationOfficer(LandVerificationOfficerDto landVerificationOfficerDto) throws CouldNotBeAddedException {
        logger.info("Entered into addLandVerificationOfficer method in LandVerification Class");
        if(landVerificationRepository.findByOfficerContact(landVerificationOfficerDto.getOfficeContact()) != null){
            throw new CouldNotBeAddedException("Officer already exists with contact");
        }
        LandVerificationOfficer landVerificationOfficer = new LandVerificationOfficer();
        landVerificationOfficer.setOfficerContact(landVerificationOfficerDto.getOfficeContact());
        landVerificationOfficer.setOfficerName(landVerificationOfficerDto.getOfficeName());
        landVerificationOfficer.setPassword(landVerificationOfficerDto.getPassword());
        BeanUtils.copyProperties(landVerificationOfficerDto, landVerificationOfficer);
        landVerificationOfficer.setRole("LAND_VERIFICATION_OFFICER");
        logger.info("Exited from addLandVerificationOfficer method in LandVerification Class");
        return landVerificationRepository.save(landVerificationOfficer);

    }
}
