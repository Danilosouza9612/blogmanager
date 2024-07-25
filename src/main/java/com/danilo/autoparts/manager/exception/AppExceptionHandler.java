package com.danilo.autoparts.manager.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.management.InstanceNotFoundException;
import java.util.List;

@ControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception){
        return new ResponseEntity<>(new MethodArgumentNotValidErrorSerialization(exception).getListMap(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({BusinessRuleViolationException.class})
    public ResponseEntity<Object> businessRuleViolationException(BusinessRuleViolationException exception){
        return new ResponseEntity<>(exception.getErrorSerialization().getListMap(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({InstanceNotFoundException.class})
    public ResponseEntity<Object> instanceNotFoundExceptionHandler(InstanceNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
