package com.demo.bankbaru.service;

import com.demo.bankbaru.entity.BankAccount;
import com.demo.bankbaru.entity.TransactionData;
import com.demo.bankbaru.entity.customenum.TransactionType;
import com.demo.bankbaru.repository.BankAccountRepository;
import com.demo.bankbaru.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {

    private final BankAccountRepository bankAccountRepository;

    private final TransactionRepository transactionRepository;

    @Transactional
    public void performTransfer(int fromAccountId, int toAccountId, BigDecimal amount, String description) {

        BankAccount fromAccount = bankAccountRepository.findById(fromAccountId)
                .orElseThrow(() -> new EntityNotFoundException("Sender account not found with id: " + fromAccountId));

        BankAccount toAccount = bankAccountRepository.findById(toAccountId)
                .orElseThrow(() -> new EntityNotFoundException("Receiver account not found with id: " + toAccountId));

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient funds in account " + fromAccountId);
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        TransactionData withdrawal = new TransactionData();
        withdrawal.setBankAccount(fromAccount);
        withdrawal.setAmount(amount);
        withdrawal.setType(TransactionType.TRANSFER_OUT);
        withdrawal.setDescription(description);
        withdrawal.setTransactionDate(LocalDateTime.now());

        TransactionData deposit = new TransactionData();
        deposit.setBankAccount(toAccount);
        deposit.setAmount(amount);
        deposit.setType(TransactionType.TRANSFER_IN);
        deposit.setDescription(description);
        deposit.setTransactionDate(LocalDateTime.now());

        bankAccountRepository.save(fromAccount);
        bankAccountRepository.save(toAccount);
        transactionRepository.save(withdrawal);
        transactionRepository.save(deposit);

    }

    public TransactionData createTransaction(int accountId, BigDecimal amount, TransactionType type, String description) {
        TransactionData transactionData = new TransactionData();
        transactionData.setAmount(amount);
        transactionData.setDescription(description);
        transactionData.setType(type);
        transactionData.setTransactionDate(LocalDateTime.now());
        transactionData.setBankAccount(bankAccountRepository.getById(accountId));

        transactionRepository.save(transactionData);

        return transactionData;
    }

    public List<TransactionData> getTransactionsForAccount(int accountId) {
        return transactionRepository.getTransactionDataByBankAccountId(accountId);
    }
}