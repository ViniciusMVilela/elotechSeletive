package com.elotech.process.crud.model.infra;

import com.elotech.process.crud.model.exception.NotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalConfigExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFoundEntity(NotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraint(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder errorMessage = new StringBuilder();
        for (ConstraintViolation<?> violation : violations) {
            errorMessage.append("Property: ").append(violation.getPropertyPath()).append(", ")
                    .append("Message: ").append(violation.getMessage()).append("; ");
        }
        return new ResponseEntity<>("Validation Error: " + errorMessage.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleIntegraty(Exception e) {
        return new ResponseEntity<>("Please confirm fields, datas can already exists", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException e) {
        List<String> errorMessages = e.getBindingResult().getFieldErrors().stream()
                .map(error -> "Field: " + error.getField() + ", Message: " + error.getDefaultMessage())
                .collect(Collectors.toList());

        String errorMessage = String.join("; ", errorMessages);

        return new ResponseEntity<>("Validation Error: " + errorMessage, HttpStatus.BAD_REQUEST);
    }
}
