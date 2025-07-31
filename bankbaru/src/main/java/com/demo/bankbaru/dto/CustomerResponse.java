package com.demo.bankbaru.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private String customerName;
    private String customerAddress;
    private String phoneNumber;
    private String email;
}
