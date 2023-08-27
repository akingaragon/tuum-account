package com.tuum.account.service.db;

import com.tuum.account.entity.AccountBalance;
import com.tuum.account.enums.Currency;
import com.tuum.account.mapper.AccountBalanceMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.tuum.account.util.IntegrationTestHelper.ACCOUNT_ID;

@ExtendWith(MockitoExtension.class)
class AccountBalanceDatabaseServiceTest {

    @InjectMocks
    AccountBalanceDatabaseService accountBalanceDatabaseService;

    @Mock
    AccountBalanceMapper accountBalanceMapper;

    @Test
    void getAccountBalances() {
        accountBalanceDatabaseService.getAccountBalances(ACCOUNT_ID);
        Mockito.verify(accountBalanceMapper, Mockito.times(1)).getAccountBalancesByAccountId(ACCOUNT_ID);
    }

    @Test
    void insertAccountBalance() {
        AccountBalance accountBalance = new AccountBalance(ACCOUNT_ID, Currency.EUR);
        accountBalanceDatabaseService.insertAccountBalance(accountBalance);
        Mockito.verify(accountBalanceMapper, Mockito.times(1)).insertAccountBalance(accountBalance);
    }

    @Test
    void getAccountBalancesByAccountIdAndCurrency() {
        accountBalanceDatabaseService.getAccountBalancesByAccountIdAndCurrency(ACCOUNT_ID, Currency.EUR);
        Mockito.verify(accountBalanceMapper, Mockito.times(1)).getAccountBalancesByAccountIdAndCurrency(ACCOUNT_ID, Currency.EUR);
    }

    @Test
    void updateAccountBalance() {
        AccountBalance accountBalance = new AccountBalance(ACCOUNT_ID, Currency.EUR);
        accountBalanceDatabaseService.updateAccountBalance(accountBalance);
        Mockito.verify(accountBalanceMapper, Mockito.times(1)).updateAccountBalance(accountBalance);
    }
}