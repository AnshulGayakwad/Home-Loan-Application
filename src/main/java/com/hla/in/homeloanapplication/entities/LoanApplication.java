package com.hla.in.homeloanapplication.entities;

import com.hla.in.homeloanapplication.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long applicationId;
    @Column
    private LocalDate applicationDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_Info", referencedColumnName = "userId")
    private Customer customer;

    @Column
    private double loanAppliedAmount;
    @Column
    private double loanApprovedAmount;
    @Column
    private boolean landVerificationApproval;
    @Column
    private boolean financeVerificationApproval;
    @Column
    private boolean adminApproval;
    @Column
    @Enumerated(EnumType.STRING)
    private Status status;
    @NotNull
    private double totalAnnualIncome;
    @NotNull
    private double monthlyExpenses;
    @NotNull
    private double otherMonthlyExpenses;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "scheme_scheme_id", referencedColumnName = "schemeId")
    Scheme scheme;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_loanagrmnt_id",referencedColumnName = "loanAgreementId")
    LoanAgreement loanAgreement;

}
