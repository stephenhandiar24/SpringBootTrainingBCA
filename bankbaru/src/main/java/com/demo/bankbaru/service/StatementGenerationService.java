package com.demo.bankbaru.service;

import com.demo.bankbaru.entity.AccountStatement;
import com.demo.bankbaru.entity.BankAccount;
import com.demo.bankbaru.entity.TransactionData;
import com.demo.bankbaru.entity.customenum.TransactionType;
import com.demo.bankbaru.repository.AccountStatementRepository;
import com.demo.bankbaru.repository.BankAccountRepository;
import com.demo.bankbaru.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatementGenerationService {

    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;
    private final AccountStatementRepository accountStatementRepository;

    @Transactional
    public AccountStatement generateMonthlyStatement(int bankAccountId, int year, int month) {
        BankAccount account = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new EntityNotFoundException("Bank Account not found with id: " + bankAccountId));

        System.out.println("Generating statement for customer: " + account.getCustomer().getCustomerName());

        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(LocalTime.MAX);

        List<TransactionData> transactionsInPeriod = transactionRepository.findByBankAccountAndTransactionDateBetween(
                account, startOfMonth, endOfMonth);

        BigDecimal totalChange = transactionsInPeriod.stream()
                .map(tx -> tx.getType() == TransactionType.DEPOSIT ? tx.getAmount() : tx.getAmount().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal closingBalance = account.getBalance();
        BigDecimal openingBalance = closingBalance.subtract(totalChange);

        AccountStatement statement = new AccountStatement();
        statement.setBankAccount(account);
        statement.setStatementStartDate(startOfMonth.toLocalDate());
        statement.setStatementEndDate(endOfMonth.toLocalDate());
        statement.setGeneratedAt(LocalDateTime.now());
        statement.setOpeningBalance(openingBalance);
        statement.setClosingBalance(closingBalance);

        return accountStatementRepository.save(statement);
    }
}