package com.demo.bankbaru.repository;

import com.demo.bankbaru.entity.BankAccount;
import com.demo.bankbaru.entity.TransactionData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionData, Long> {
    List<TransactionData> findByBankAccountAndTransactionDateBetween(BankAccount accountId, LocalDateTime start, LocalDateTime end);
    List<TransactionData> findByBankAccountIdAndTransactionDateBetween(Integer accountId, LocalDateTime start, LocalDateTime end);

    List<TransactionData> getTransactionDataByBankAccountId(int accountId);
}