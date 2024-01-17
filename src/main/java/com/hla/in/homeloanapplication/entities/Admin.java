package com.hla.in.homeloanapplication.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends User{
    private String adminName;
    private String adminContact;

    public Admin(int userId, String password, String role, String adminName, String adminContact) {
        super(userId, password, role);
        this.adminName = adminName;
        this.adminContact = adminContact;
    }
}
