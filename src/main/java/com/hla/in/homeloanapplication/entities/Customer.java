package com.hla.in.homeloanapplication.entities;

import lombok.*;

import javax.persistence.Entity;
import java.time.LocalDate;
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@EqualsAndHashCode(callSuper = true)
public class Customer extends User{
    private String customerName;
    private String mobileNumber;
    private String emailId;
    private LocalDate dateOfBirth;
    private String gender;
    private String nationality;
    private String aadharNumber;
    private String panNumber;

}