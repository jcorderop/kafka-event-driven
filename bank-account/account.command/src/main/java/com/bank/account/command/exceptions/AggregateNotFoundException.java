package com.bank.account.command.exceptions;

public class AggregateNotFoundException extends RuntimeException {
    public AggregateNotFoundException(String s) {
        super(s);
    }
}
