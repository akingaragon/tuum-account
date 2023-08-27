package com.tuum.account.service;

import com.tuum.account.dto.request.CreateAccountRequest;
import com.tuum.account.dto.response.AccountDto;
import com.tuum.account.entity.Account;
import com.tuum.account.entity.AccountBalance;
import com.tuum.account.enums.AccountStatus;
import com.tuum.account.enums.Currency;
import com.tuum.account.service.db.AccountDatabaseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static com.tuum.account.util.IntegrationTestHelper.*;

@ExtendWith(MockitoExtension.class)
public class AccountManagementServiceTest {

    @InjectMocks
    private AccountManagementService accountManagementService;

    @Mock
    private AccountDatabaseService accountService;

    @Mock
    private AccountBalanceManagementService accountBalanceManagementService;


    @ParameterizedTest
    @EnumSource(Currency.class)
    void createAccountWithInitialBalances(Currency currency) {

        Account account = Account.builder().id(ACCOUNT_ID).customerId(CUSTOMER_ID).country(COUNTRY).status(AccountStatus.ACTIVE).build();
        AccountBalance accountBalance = new AccountBalance(account.getId(), currency);

        Mockito.when(accountService.createAccount(CUSTOMER_ID, COUNTRY)).thenReturn(account);
        Mockito.when(accountBalanceManagementService.createAccountBalance(ACCOUNT_ID, currency)).thenReturn(accountBalance);

        CreateAccountRequest createAccountRequest = new CreateAccountRequest(CUSTOMER_ID, COUNTRY, List.of(currency));
        AccountDto accountDto = accountManagementService.createAccountWithInitialBalances(createAccountRequest);

        Assertions.assertEquals(CUSTOMER_ID, accountDto.customerId());
        Assertions.assertEquals(ACCOUNT_ID, accountDto.accountId());
        accountDto.balances().forEach(k -> Assertions.assertEquals(BigDecimal.ZERO, k.availableAmount()));
    }


    @ParameterizedTest
    @EnumSource(Currency.class)
    void getAccount(Currency currency) {
        Account account = Account.builder().id(ACCOUNT_ID).customerId(CUSTOMER_ID).country(COUNTRY).status(AccountStatus.ACTIVE).build();
        AccountBalance accountBalance = new AccountBalance(account.getId(), currency);

        Mockito.when(accountService.getAccountById(ACCOUNT_ID)).thenReturn(account);
        Mockito.when(accountBalanceManagementService.getAccountBalancesByAccountId(ACCOUNT_ID)).thenReturn(List.of(accountBalance));

        AccountDto accountDto = accountManagementService.getAccount(ACCOUNT_ID);

        Assertions.assertEquals(accountDto.balances().get(0).availableAmount(), accountBalance.getAvailableAmount());
        Assertions.assertEquals(accountDto.balances().get(0).currency(), accountBalance.getCurrency());

    }
}
