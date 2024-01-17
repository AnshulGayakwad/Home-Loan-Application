package com.hla.in.homeloanapplication.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    //private int userId;

    @NotEmpty(message = "Customer Name can't be empty!")
    @Size(min = 2, max = 25, message = "Length of Customer name should be greater than 2 & less than 25")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Accepts only alphabets! Without Space")
    private String customerName;

    @NotEmpty(message = "Password can't be empty!")
    @Size(min = 8, max = 20, message = "Password has to be of minimum 8 characters")
    private String password;

    @NotEmpty(message = "Contact cannot be Empty")
    @Pattern(regexp = "^\\d{10}$", message = "Invalid input:Enter numbers only")
    @Size(min = 10, max = 10, message = "Please provide exact 10 digit phone number")
    private  String mobileNumber;

    @NotEmpty(message = "Email ID can't be empty!")
    @Email(message = "Please enter a valid email address")
    private String emailId;

    @NotNull(message = "Please enter a valid Date Of Birth in the form of 'dd/MM/yyyy'")
    @NotEmpty(message = "DOB cannot be empty")
    private String dateOfBirth;

    @NotEmpty(message = "Please Provide Gender")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Please enter Alphabets only")
    private String gender;

    @NotEmpty(message = "Please enter your Nationality")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Please enter Alphabets only")
    private String nationality;

    @NotEmpty(message = "Please enter your Aadhar Card number")
    @Size(min = 12, max = 12, message = "Please enter 12 digit Aadhar No.")
    private String aadharNumber;

    @NotEmpty(message = "Please enter your PAN Card number")
    @Size(min = 10, max = 10, message = "Please enter 10 digit PAN!")
    private String panNumber;
}
