package com.demo.bankbaru.service;

import com.demo.bankbaru.entity.AccountStatement;
import com.demo.bankbaru.entity.BankAccount;
import com.demo.bankbaru.entity.TransactionData;
import com.demo.bankbaru.entity.customenum.TransactionType;
import com.demo.bankbaru.repository.AccountStatementRepository;
import com.demo.bankbaru.repository.BankAccountRepository;
import com.demo.bankbaru.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountStatementService {

    private final BankAccountRepository bankAccountRepository;

    private final TransactionRepository transactionRepository;

    private final AccountStatementRepository accountStatementRepository;

    @Transactional(readOnly = true)
    public AccountStatement generateMonthlyStatement(int accountId, int year, int month) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + accountId));

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());

        List<TransactionData> transactionsInPeriod = transactionRepository.findByBankAccountIdAndTransactionDateBetween(
                accountId,
                startDate.atStartOfDay(),
                endDate.atTime(LocalTime.MAX)
        );

        BigDecimal closingBalance = account.getBalance();
        BigDecimal netChange = transactionsInPeriod.stream()
                .map(tx -> tx.getType() == TransactionType.DEPOSIT || tx.getType() == TransactionType.TRANSFER_IN
                        ? tx.getAmount()
                        : tx.getAmount().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal openingBalance = closingBalance.subtract(netChange);

        AccountStatement statement = new AccountStatement();
        statement.setBankAccount(account);
        statement.setStatementStartDate(startDate);
        statement.setStatementEndDate(endDate);
        statement.setGeneratedAt(LocalDateTime.now());
        statement.setOpeningBalance(openingBalance);
        statement.setClosingBalance(closingBalance);

         accountStatementRepository.save(statement);

        return statement;
    }
}