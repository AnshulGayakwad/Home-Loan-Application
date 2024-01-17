package com.hla.in.homeloanapplication.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EMI {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long emiId;
	private LocalDate dueDate;
	private double emiAmount;
	private double loanAmount;
	private double interestAmount;
}
