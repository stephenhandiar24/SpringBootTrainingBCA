package com.demo.bankbaru.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class AccountStatement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate statementStartDate;
    private LocalDate statementEndDate;
    private LocalDateTime generatedAt;

    private BigDecimal openingBalance;
    private BigDecimal closingBalance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

     @OneToMany
     @JoinColumn(name = "statement_id")
     private List<TransactionData> transactions;
}