package com.manerajona.openapigen.rs;

import com.manerajona.openapigen.NotFoundException;
import com.manerajona.openapigen.rs.model.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.List;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(BindException.class)
    ResponseEntity<List<ErrorDetails>> handleBindException(BindException ex) {
        List<ErrorDetails> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> {
                    ErrorDetails errorDetails = new ErrorDetails();
                    errorDetails.setCode(400);
                    errorDetails.setDetail(fieldError.getDefaultMessage());
                    errorDetails.setField(fieldError.getField());
                    errorDetails.setValue(fieldError.getRejectedValue());
                    errorDetails.setLocation(ErrorDetails.LocationEnum.BODY);
                    return errorDetails;
                }).toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<List<ErrorDetails>> handleConstraintViolationException(ConstraintViolationException ex) {
        List<ErrorDetails> errors = ex.getConstraintViolations().stream()
                .map(constraintViolation -> {
                    ErrorDetails errorDetails = new ErrorDetails();
                    errorDetails.setCode(400);
                    errorDetails.setDetail(constraintViolation.getMessage());
                    errorDetails.setField(constraintViolation.getPropertyPath().toString());
                    errorDetails.setValue(constraintViolation.getInvalidValue());
                    errorDetails.setLocation(ErrorDetails.LocationEnum.PATH);
                    return errorDetails;
                }).toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFoundException() {
    }
}