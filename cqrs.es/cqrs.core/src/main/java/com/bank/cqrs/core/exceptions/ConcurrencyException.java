package com.bank.cqrs.core.exceptions;

public class ConcurrencyException extends RuntimeException {
    public ConcurrencyException(String s) {
        super(s);
    }
}
