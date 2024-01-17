package com.hla.in.homeloanapplication.entities;

import lombok.*;

import javax.persistence.Entity;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LandVerificationOfficer extends User{
    private String officerName;
    private String officerContact;

}
