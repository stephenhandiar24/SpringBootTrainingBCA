package com.demo.bankbaru.dto;

import com.demo.bankbaru.validator.constrain.UniqueEmail;
import com.demo.bankbaru.validator.constrain.UniquePhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    private String customerName;
    private String customerAddress;
    @UniquePhoneNumber
    private String phoneNumber;
    @UniqueEmail
    private String email;
}
