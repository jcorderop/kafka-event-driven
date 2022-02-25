package com.bank.account.command.exceptions;

public class ConcurrencyException extends RuntimeException {
    public ConcurrencyException(String s) {
        super(s);
    }
}
