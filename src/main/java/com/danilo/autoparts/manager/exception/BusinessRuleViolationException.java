package com.danilo.autoparts.manager.exception;

import lombok.Getter;

@Getter
public class BusinessRuleViolationException extends RuntimeException{
    private final ErrorSerialization errorSerialization;

    public BusinessRuleViolationException(ErrorSerialization errorSerialization){
        this.errorSerialization = errorSerialization;
    }
}
