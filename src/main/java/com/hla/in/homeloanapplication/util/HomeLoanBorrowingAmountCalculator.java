package com.hla.in.homeloanapplication.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeLoanBorrowingAmountCalculator {
	
	private double loanAmount;
	private double rateOfInterest;
	private int tenure;
	private double totalAnnualIncome;
	private double monthlyExpenses;
	private double otherMonthlyExpenses;

	public double getHomeLoanBorrowingAmount() {
		double monthlyIncome = this.totalAnnualIncome/12;
		if(monthlyIncome < 15000.0) {
			return 0.0;
		}
		double annualSavings = (this.totalAnnualIncome - (12*(this.monthlyExpenses+this.otherMonthlyExpenses)));
		double eligibleAmount = annualSavings/(this.tenure) + ((this.rateOfInterest*annualSavings)/100);
		if(eligibleAmount > this.loanAmount){
			return this.loanAmount;
		}
		return eligibleAmount;
	}

}
