package com.hla.in.homeloanapplication.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {
    @NotNull(message = "userId cannot be null")
    int userId;

    @NotEmpty(message = "Password can't be empty!")
    @Size(min = 8, max = 20, message = "Password has to be of minimum 8 characters")
    private String password;
}
