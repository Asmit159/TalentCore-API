package com.luv2code.springboot.cruddemo.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class EmployeeRestExceptionHandler {

    //Employee not found
    @ExceptionHandler
    public ResponseEntity<EmployeeErrorResponse> handleException(
            EmployeeNotFoundException exc) {

        EmployeeErrorResponse error = new EmployeeErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exc.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    //Invalid path variable (e.g. /employees/abc)
    @ExceptionHandler
    public ResponseEntity<EmployeeErrorResponse> handleException(
            MethodArgumentTypeMismatchException exc) {

        EmployeeErrorResponse error = new EmployeeErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid employee id. ID must be numeric.",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    //Unsupported HTTP method (e.g. POST on /employees/{id})
    @ExceptionHandler
    public ResponseEntity<EmployeeErrorResponse> handleException(
            HttpRequestMethodNotSupportedException exc) {

        EmployeeErrorResponse error = new EmployeeErrorResponse(
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                "HTTP method not supported for this endpoint.",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }

    //Fallback
    @ExceptionHandler
    public ResponseEntity<EmployeeErrorResponse> handleException(
            RuntimeException exc) {

        EmployeeErrorResponse error = new EmployeeErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal server error. Please contact support.",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<EmployeeErrorResponse> handleException(
            DataIntegrityViolationException exc) {

        EmployeeErrorResponse error = new EmployeeErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Email already exists. Please use a unique email.",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
