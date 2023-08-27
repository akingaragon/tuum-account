package com.tuum.account.dto.response;

import java.math.BigDecimal;

public record CreateTransactionResponseDto(TransactionDto transactionDto, BigDecimal balanceAfterTransaction) {
}
