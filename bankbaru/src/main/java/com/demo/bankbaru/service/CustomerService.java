package com.demo.bankbaru.service;

import com.demo.bankbaru.dto.CustomerRequest;
import com.demo.bankbaru.dto.CustomerResponse;
import com.demo.bankbaru.entity.BankAccount;
import com.demo.bankbaru.entity.Customer;
import com.demo.bankbaru.repository.BankAccountRepository;
import com.demo.bankbaru.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private  final CustomerRepository customerRepository;
    private  final BankAccountRepository bankAccountRepository;
    private  final ModelMapper modelMapper;
    public CustomerResponse saveCustomer(CustomerRequest customerRequest){
        Customer customer = modelMapper.map(customerRequest, Customer.class);
        customerRepository.save(customer);
        return modelMapper.map(customer,CustomerResponse.class);
    }

    public Page<Customer> getCustomerByPhoneNumber(Pageable pageable, String phoneNumber){
        return customerRepository.getCustomerByPhoneNumber(phoneNumber,pageable);
    }
    public CustomerResponse updateCustomer(Integer id, CustomerRequest customerRequest) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        modelMapper.map(customerRequest, existingCustomer);
        customerRepository.save(existingCustomer);
        return modelMapper.map(existingCustomer,CustomerResponse.class);

    }
    public void testLazy() {
        System.out.println("=== LAZY TEST START ===");
        Customer customer = customerRepository.findById(1).orElseThrow();

        System.out.println("Customer Name: " + customer.getCustomerName());
        System.out.println("Jumlah Akun: " + customer.getBankAccountList().size());
    }

    public void testEager() {
        System.out.println("=== EAGER TEST START ===");
        BankAccount account = bankAccountRepository.findById(1).orElseThrow();

        System.out.println("Account No: " + account.getCustomer().getId());
        System.out.println("Customer Name: " + account.getCustomer().getCustomerName());
    }
    public Page<Customer> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public Page<Customer> getName(Pageable pageable, String name) {
        return customerRepository.findByCustomerName(name, pageable);
    }
}
