package com.hla.in.homeloanapplication.service;

import com.hla.in.homeloanapplication.entities.User;
public interface IUserService {

    public User addNewUser(User user);
    public User signIn(User user);
    public User signOut(User user);
}
