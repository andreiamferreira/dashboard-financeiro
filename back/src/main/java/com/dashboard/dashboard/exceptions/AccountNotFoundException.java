package com.dashboard.dashboard.exceptions;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException() {
        super("Account not found.");
    }

    public AccountNotFoundException(String message) {
        super(message);
    }

}
