package com.hla.in.homeloanapplication.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {
    private int userId;
    @NotEmpty(message = "Password can't be empty!")
    @Size(min = 8, max = 20, message = "Password has to be of minimum 8 characters")
//    @Pattern(regexp = "^(?=.*\\d)"
//            + "(?=.*[a-z])(?=.*[A-Z])"
//            + "(?=.*[@#$%^&+=])"
//            + "(?=\\S+$).{8,20}$",message = "Password Invalid : password should contain atleast one of the " +
//            "following:- Uppercase alphabet, Lowercase alpabet, Digit,Special charecter, Minimum length of password should be 8 charecters")
    private String password;

    @NotEmpty(message = "Admin Name can't be empty!")
    @Size(min = 2, max = 25, message = "Length of admin name should be greater than 2 & less than 25")
    //@Pattern(regexp = "^[a-zA-Z]*$", message = "Accepts only alphabets! re-enter the admin name")
    private String adminName;

    @NotEmpty(message = "Please Provide Phone Number")
    @Size(min = 10, max = 10, message = "Please provide exact 10 digit phone number")
    @Pattern(regexp = "^\\d{10}$", message = "Invalid input:Enter numbers only")
    private String adminContact;
}
