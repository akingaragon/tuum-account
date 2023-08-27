package com.tuum.account.service;

import com.tuum.account.entity.AccountBalance;
import com.tuum.account.enums.Currency;
import com.tuum.account.service.db.AccountBalanceDatabaseService;
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

import static com.tuum.account.util.IntegrationTestHelper.ACCOUNT_ID;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AccountBalanceManagementServiceTest {

    @InjectMocks
    private AccountBalanceManagementService accountBalanceManagementService;

    @Mock
    private AccountBalanceDatabaseService accountBalanceDatabaseService;


    @ParameterizedTest
    @EnumSource(Currency.class)
    void getAccountBalancesByAccountId(Currency currency) {
        AccountBalance accountBalance = new AccountBalance(ACCOUNT_ID, currency);

        Mockito.when(accountBalanceDatabaseService.getAccountBalances(ACCOUNT_ID)).thenReturn(List.of(accountBalance));

        List<AccountBalance> accountBalances = accountBalanceManagementService.getAccountBalancesByAccountId(ACCOUNT_ID);

        Assertions.assertEquals(1, accountBalances.size());
        Assertions.assertEquals(accountBalance, accountBalances.get(0));

        Mockito.verify(accountBalanceDatabaseService, Mockito.times(1)).getAccountBalances(ACCOUNT_ID);

    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    void createAccountBalance(Currency currency) {

        AccountBalance accountBalance = accountBalanceManagementService.createAccountBalance(ACCOUNT_ID, currency);

        Assertions.assertEquals(ACCOUNT_ID, accountBalance.getAccountId());
        Assertions.assertEquals(currency, accountBalance.getCurrency());
        Assertions.assertEquals(BigDecimal.ZERO, accountBalance.getAvailableAmount());

        Mockito.verify(accountBalanceDatabaseService, Mockito.times(1)).insertAccountBalance(any(AccountBalance.class));
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    void getAccountBalance(Currency currency) {
        accountBalanceManagementService.getAccountBalance(ACCOUNT_ID, currency);
        Mockito.verify(accountBalanceDatabaseService, Mockito.times(1)).getAccountBalancesByAccountIdAndCurrency(ACCOUNT_ID, currency);
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    void updateAvailableAmount(Currency currency) {
        AccountBalance accountBalance = new AccountBalance(ACCOUNT_ID, currency);
        accountBalanceManagementService.updateAvailableAmount(accountBalance);
        Mockito.verify(accountBalanceDatabaseService, Mockito.times(1)).updateAccountBalance(accountBalance);
    }
}