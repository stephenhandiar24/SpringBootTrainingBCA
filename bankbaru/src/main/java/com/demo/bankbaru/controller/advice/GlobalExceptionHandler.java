package com.demo.bankbaru.controller.advice;

import com.demo.bankbaru.dto.BaseResponse;
import com.demo.bankbaru.exception.ResourceNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ IllegalArgumentException.class, ConstraintViolationException.class })
    public ResponseEntity<BaseResponse<Object>> handleBadRequest(Exception ex) {
        return ResponseEntity.badRequest().body(
                BaseResponse.builder()
                        .success(false)
                        .message("Bad Request: " + ex.getMessage())
                        .code(HttpStatus.BAD_REQUEST.value())
                        .data(null)
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>> handleValidationError(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.badRequest().body(
                BaseResponse.builder()
                        .success(false)
                        .message("Validation Failed: " + errorMessage)
                        .code(HttpStatus.BAD_REQUEST.value())
                        .data(null)
                        .build()
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                BaseResponse.builder()
                        .success(false)
                        .message("Not Found: " + ex.getMessage())
                        .code(HttpStatus.NOT_FOUND.value())
                        .data(null)
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleInternalError(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                BaseResponse.builder()
                        .success(false)
                        .message("Internal Server Error: " + ex.getMessage())
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .data(null)
                        .build()
        );
    }
}