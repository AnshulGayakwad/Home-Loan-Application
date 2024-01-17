package com.hla.in.homeloanapplication.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoanAgreement {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long loanAgreementId;

	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name ="fk_emi_id",referencedColumnName = "emiId")
	EMI emi;

}

