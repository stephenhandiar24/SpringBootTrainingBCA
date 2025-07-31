package com.demo.bankbaru.repository;

import com.demo.bankbaru.entity.AccountStatement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountStatementRepository extends JpaRepository<AccountStatement, Long> {
}