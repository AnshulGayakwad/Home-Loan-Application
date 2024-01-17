package com.hla.in.homeloanapplication.entities;

import lombok.*;

import javax.persistence.Entity;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FinanceVerificationOfficer extends User{
    private String finOfficerName;
    private String finOfficerContact;
}