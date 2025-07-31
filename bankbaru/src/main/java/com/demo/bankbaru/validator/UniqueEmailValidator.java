package com.demo.bankbaru.validator;

import com.demo.bankbaru.repository.CustomerRepository;
import com.demo.bankbaru.validator.constrain.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private final CustomerRepository customerRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isBlank()) {
            return true;
        }
        return !customerRepository.existsByEmail(email);
    }

}
