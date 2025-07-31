package com.demo.bankbaru.dto;

import com.demo.bankbaru.entity.customenum.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class CreateTransactionRequest {
    private BigDecimal amount;
    private TransactionType type;
    private String description;
}