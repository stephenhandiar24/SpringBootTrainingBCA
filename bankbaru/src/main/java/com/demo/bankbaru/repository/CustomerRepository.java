package com.demo.bankbaru.repository;

import com.demo.bankbaru.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

    @Query("SELECT c FROM Customer c WHERE c.phoneNumber = :phoneNumber")
    Page<Customer> getCustomerByPhoneNumber(String phoneNumber, Pageable pageable);

    @Query("SELECT c FROM Customer c WHERE c.customerName = :customerName")
    Page<Customer> findByCustomerName(String customerName, Pageable pageable);
}
