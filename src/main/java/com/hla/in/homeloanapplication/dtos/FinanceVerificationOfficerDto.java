package com.hla.in.homeloanapplication.dtos;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FinanceVerificationOfficerDto {
    @NotEmpty(message = "Please Provide Finance Verification Officer's Name")
    @Size(min = 2, max = 25, message = "Length of Officer's name should be greater than 2 & less than 25")
    //@Pattern(regexp = "^[a-zA-Z]*$", message = "Accepts only alphabets! re-enter the admin name")
    private String finOfficerName;

    @NotEmpty(message = "Please Provide Phone Number")
    @Size(min = 10, max = 10, message = "Please provide exact 10 digit phone number")
    @Pattern(regexp = "^\\d{10}$", message = "Invalid input:Enter numbers only")
    private String finOfficerContact;

    @Hidden
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;

    @NotEmpty(message = "Password can't be empty!")
    @Size(min = 8, max = 20, message = "Password has to be of minimum 8 characters")
    private String password;
}
