package com.hla.in.homeloanapplication.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EMICalculator {
	
	private double loanAmount;
	private double rateOfInterest;
	private int tenure;

	public double getEMIAmount() {
		return (loanAmount+(loanAmount*rateOfInterest))/(tenure);
	}

}

