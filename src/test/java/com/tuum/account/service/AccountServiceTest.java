package com.tuum.account.service;

import com.tuum.account.dto.request.CreateAccountRequest;
import com.tuum.account.dto.response.AccountDto;
import com.tuum.account.entity.Account;
import com.tuum.account.entity.AccountBalance;
import com.tuum.account.enums.AccountStatus;
import com.tuum.account.enums.Country;
import com.tuum.account.enums.Currency;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static com.tuum.account.enums.Country.EE;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    public static final Long CUSTOMER_ID = 1L;
    public static final Long ACCOUNT_ID = 2L;
    public static final Country COUNTRY = EE;

    @InjectMocks
    private AccountManagementService accountManagementService;

    @Mock
    private AccountService accountService;

    @Mock
    private AccountBalanceService accountBalanceService;


    @ParameterizedTest
    @EnumSource(Currency.class)
    void createAccountWithInitialBalances(Currency currency) {

        Account account = Account.builder().id(ACCOUNT_ID).customerId(CUSTOMER_ID).country(COUNTRY).status(AccountStatus.ACTIVE).build();
        AccountBalance accountBalance = new AccountBalance(account.getId(), currency);

        Mockito.when(accountService.createAccount(CUSTOMER_ID, COUNTRY)).thenReturn(account);
        Mockito.when(accountBalanceService.createAccountBalance(ACCOUNT_ID, currency)).thenReturn(accountBalance);

        CreateAccountRequest createAccountRequest = new CreateAccountRequest(CUSTOMER_ID, COUNTRY, List.of(currency));
        AccountDto accountDto = accountManagementService.createAccountWithInitialBalances(createAccountRequest);

        Assertions.assertThat(CUSTOMER_ID.equals(accountDto.customerId()));
        Assertions.assertThat(ACCOUNT_ID.equals(accountDto.accountId()));
        accountDto.balances().forEach(k -> Assertions.assertThat(BigDecimal.ZERO.equals(k.availableAmount())));
    }

}
