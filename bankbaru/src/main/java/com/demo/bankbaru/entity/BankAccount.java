package com.demo.bankbaru.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String bankName;
    private String cardNumber;

    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<TransactionData> transactions;

    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AccountStatement> statements;
}