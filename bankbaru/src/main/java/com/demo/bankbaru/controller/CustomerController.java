package com.demo.bankbaru.controller;

import com.demo.bankbaru.dto.CustomerRequest;
import com.demo.bankbaru.dto.CustomerResponse;
import com.demo.bankbaru.entity.Customer;
import com.demo.bankbaru.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest customerRequest){
        CustomerResponse response = customerService.saveCustomer(customerRequest);
        return ResponseEntity.ok(response);
    }
    @PatchMapping
    public ResponseEntity<CustomerResponse> updateCustomer(@Valid @RequestBody CustomerRequest customerRequest, @RequestParam Integer id){
        CustomerResponse response = customerService.updateCustomer(id,customerRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public Page<Customer> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return customerService.findAll(pageable);
    }
    @GetMapping("/customer-name")
    public Page<Customer> getByCustomerName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending,
            @RequestParam String customerName
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return customerService.getName(pageable,customerName);
    }
    @GetMapping("/phone-number")
    public Page<Customer> getByPhoneNumber(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending,
            @RequestParam String customerName
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return customerService.getCustomerByPhoneNumber(pageable,customerName);
    }

    @GetMapping("/test-lazy")
    public String checkLazy(){
        customerService.testLazy();
        return "Lazy.....";
    }

    @GetMapping("/test-eager")
    public String checkEager(){
        customerService.testEager();
        return "Eager.....";
    }
}
