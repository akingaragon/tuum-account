package com.tuum.account.exception.business;

public class AccountNotFoundException extends RuntimeException implements AccountBusinessException{

    public AccountNotFoundException(Long accountId) {
        super("Account with ID " + accountId + " not found");
    }
}
