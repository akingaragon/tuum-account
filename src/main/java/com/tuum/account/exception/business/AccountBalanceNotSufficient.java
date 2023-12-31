package com.tuum.account.exception.business;

public class AccountBalanceNotSufficient extends RuntimeException implements AccountBusinessException{

    public AccountBalanceNotSufficient(Long accountId) {
        super(String.format("Account %s doesn't have sufficient fund for this transaction", accountId));
    }
}