package com.tuum.account.dto.request;

import com.tuum.account.enums.Country;
import com.tuum.account.enums.Currency;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateAccountRequest(
    @NotNull(message = "Customer id can not be null")
    Long customerId,

    @NotNull(message = "Country can not be null")
    Country country,

    @NotEmpty(message = "Currency list cannot be empty")
    List<Currency> currencyList) {

}
