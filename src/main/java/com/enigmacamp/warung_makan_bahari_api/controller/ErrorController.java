package com.enigmacamp.warung_makan_bahari_api.controller;

import com.enigmacamp.warung_makan_bahari_api.dto.response.CommonResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> responseStatusException(ResponseStatusException e) {
        CommonResponse<?> response = CommonResponse.builder()
                .statusCode(e.getStatusCode().value())
                .message(e.getReason())
                .build();
        return ResponseEntity
                .status(e.getStatusCode())
                .body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> constraintViolationException(ConstraintViolationException e) {
        CommonResponse<?> response = CommonResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}
