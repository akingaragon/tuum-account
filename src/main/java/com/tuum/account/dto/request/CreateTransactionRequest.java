package com.tuum.account.dto.request;

import com.tuum.account.enums.Currency;
import com.tuum.account.enums.TransactionDirection;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateTransactionRequest(
        @NotNull(message = "Account id can not be null")
        @Positive(message = "Please enter a valid positive account id")
        Long accountId,

        @NotNull(message = "Amount can not be null")
        @Positive(message = "Please enter a valid positive amount")
        BigDecimal amount,

        @NotNull(message = "Currency can not be null")
        Currency currency,

        @NotNull(message = "Direction of transaction can not be null")
        TransactionDirection transactionDirection,

        @NotEmpty(message = "Description can not be empty")
        String description) {

}
