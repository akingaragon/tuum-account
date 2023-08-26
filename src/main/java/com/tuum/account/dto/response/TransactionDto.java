package com.tuum.account.dto.response;

import com.tuum.account.enums.Currency;
import com.tuum.account.enums.TransactionDirection;

import java.math.BigDecimal;

public record TransactionDto(Long transactionId, Long accountId, BigDecimal amount, Currency currency, TransactionDirection transactionDirection, String description, BigDecimal balanceAfterTransaction) {
}
