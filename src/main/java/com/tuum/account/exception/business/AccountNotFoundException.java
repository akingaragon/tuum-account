package com.tuum.account.exception.business;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(Long accountId) {
        super("Account with ID " + accountId + " not found");
    }
}
