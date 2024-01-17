package com.hla.in.homeloanapplication.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocsDto {
    @NotNull(message = "Aadhaar number cannot be null")
    @Pattern(regexp = "^[2-9]\\d{11}$", message = "Invalid Aadhaar number")
    String aadharNumber;

    @NotNull(message = "Pan number cannot be null")
    @Pattern(regexp = "[A-Z]{5}\\d{4}[A-Z]{1}", message = "Invalid pan number")
    String panNumber;
}
