package com.tuum.account.dto.response;

import com.tuum.account.enums.Currency;

import java.math.BigDecimal;

public record AccountBalanceDto(BigDecimal availableAmount, Currency currency) {

}
