package com.tuum.account.util;

import com.tuum.account.dto.request.CreateAccountRequest;
import com.tuum.account.dto.response.AccountBalanceDto;
import com.tuum.account.dto.response.AccountDto;
import com.tuum.account.enums.Country;
import com.tuum.account.enums.Currency;

import java.math.BigDecimal;
import java.util.List;

public class IntegrationTestHelper {
    public static final Long ACCOUNT_ID = 1L;
    public static final Long TRANSACTION_ID = 1L;
    private static final Long CUSTOMER_ID = 1L;
    private static final Country COUNTRY = Country.EE;

    public static CreateAccountRequest getCreateAccountRequest() {
        return new CreateAccountRequest(CUSTOMER_ID, COUNTRY, List.of(Currency.EUR));
    }

    public static AccountDto createAccountDto() {
        return new AccountDto(ACCOUNT_ID, CUSTOMER_ID, List.of(new AccountBalanceDto(BigDecimal.ZERO, Currency.EUR)));
    }
}
