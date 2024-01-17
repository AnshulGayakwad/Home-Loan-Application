package com.hla.in.homeloanapplication.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SchemeDto {

    @NotEmpty(message = "Please enter a scheme name")
    @NotNull(message = "Please provide New scheme Name")
    private String schemeName;

    @NotNull(message = "Interest Rate cannot be null")
    @Min(value = 3,message ="Interest rate cannot be less than 3")
    private double interestRate;

    @NotNull(message = "Tenure cannot be null")
    @Min(value = 1,message ="Tenure cannot be less than 1")
    private int tenure;
}
