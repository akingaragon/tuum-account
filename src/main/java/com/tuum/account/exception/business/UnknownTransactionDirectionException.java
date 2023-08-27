package com.tuum.account.exception.business;

import com.tuum.account.enums.TransactionDirection;

public class UnknownTransactionDirectionException extends RuntimeException implements AccountBusinessException{

    public UnknownTransactionDirectionException(TransactionDirection transactionDirection) {
        super(String.format("Transaction direction %s is unknown", transactionDirection));
    }
}