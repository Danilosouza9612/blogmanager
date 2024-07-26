package com.danilo.blog.manager.exception;


import org.springframework.web.bind.MethodArgumentNotValidException;

public class MethodArgumentNotValidErrorSerialization extends ErrorSerialization{
    private MethodArgumentNotValidErrorSerialization() {}

    public MethodArgumentNotValidErrorSerialization(MethodArgumentNotValidException exception){
        super();
        exception.getFieldErrors().forEach(fieldError -> {
            this.addError(fieldError.getField(), fieldError.getDefaultMessage());
        });
    }
}
