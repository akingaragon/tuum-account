package com.tuum.account.exception.business;

import com.tuum.account.enums.Currency;

public class AccountBalanceNotFound extends RuntimeException implements AccountBusinessException{

    public AccountBalanceNotFound(Long accountId, Currency currency) {
        super(String.format("Account %s doesn't have a balance for %s currency", accountId, currency));
    }
}