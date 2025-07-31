package com.demo.bankbaru.validator;

import com.demo.bankbaru.repository.CustomerRepository;
import com.demo.bankbaru.validator.constrain.UniquePhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UniquePhoneNumberValidator implements ConstraintValidator<UniquePhoneNumber, String> {

    private final CustomerRepository customerRepository;

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            return true;
        }
        return !customerRepository.existsByPhoneNumber(phoneNumber);
    }
}
