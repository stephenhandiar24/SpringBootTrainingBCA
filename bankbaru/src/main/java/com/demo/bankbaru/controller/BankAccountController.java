package com.demo.bankbaru.controller;

import com.demo.bankbaru.dto.CreateTransactionRequest;
import com.demo.bankbaru.entity.AccountStatement;
import com.demo.bankbaru.entity.TransactionData;
import com.demo.bankbaru.service.StatementGenerationService;
import com.demo.bankbaru.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class BankAccountController {

    private final TransactionService transactionService;
    private final StatementGenerationService statementGenerationService;
    @PostMapping("/{accountId}/transactions")
    public ResponseEntity<TransactionData> createTransaction(
            @PathVariable int accountId,
            @RequestBody CreateTransactionRequest request) {

        TransactionData newTransaction = transactionService.createTransaction(
                accountId,
                request.getAmount(),
                request.getType(),
                request.getDescription()
        );
        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }
    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<TransactionData>> getTransactionsForAccount(@PathVariable int accountId) {
        List<TransactionData> transactions = transactionService.getTransactionsForAccount(accountId);
        return ResponseEntity.ok(transactions);
    }
    @PostMapping("/{accountId}/statements")
    public ResponseEntity<AccountStatement> generateStatement(
            @PathVariable int accountId,
            @RequestParam int year,
            @RequestParam int month) {


        AccountStatement statement = statementGenerationService.generateMonthlyStatement(accountId, year, month);
        return new ResponseEntity<>(statement, HttpStatus.CREATED);
    }
}