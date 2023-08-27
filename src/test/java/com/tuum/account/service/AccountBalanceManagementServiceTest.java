package com.tuum.account.service;

import com.tuum.account.entity.AccountBalance;
import com.tuum.account.enums.Currency;
import com.tuum.account.service.db.AccountBalanceDatabaseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.tuum.account.util.IntegrationTestHelper.ACCOUNT_ID;

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

        Assertions.assertEquals(accountBalances.size(), accountBalances.size());
        Assertions.assertEquals(accountBalances.get(0), accountBalances.get(0));

    }

    @Test
    void createAccountBalance() {
    }

    @Test
    void getAccountBalance() {
    }

    @Test
    void updateAvailableAmount() {
    }
}