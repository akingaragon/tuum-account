package com.tuum.account.service.db;

import com.tuum.account.entity.Account;
import com.tuum.account.enums.AccountStatus;
import com.tuum.account.enums.Country;
import com.tuum.account.exception.business.AccountNotFoundException;
import com.tuum.account.mapper.AccountMapper;
import com.tuum.account.rabbit.RabbitPublisher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.tuum.account.util.IntegrationTestHelper.ACCOUNT_ID;
import static com.tuum.account.util.IntegrationTestHelper.CUSTOMER_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountDatabaseServiceTest {

    @InjectMocks
    private AccountDatabaseService accountDatabaseService;

    @Mock
    private AccountMapper accountMapper;

    @Mock
    RabbitPublisher rabbitPublisher;

    @Test
    void getAccountByIdAccountNotFoundExceptionTest() {
        when(accountMapper.getAccountById(ACCOUNT_ID)).thenReturn(null);
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountDatabaseService.getAccountById(ACCOUNT_ID));
    }

    @Test
    void getAccountById() {
        when(accountMapper.getAccountById(ACCOUNT_ID)).thenReturn(new Account(ACCOUNT_ID, CUSTOMER_ID, Country.EE, AccountStatus.ACTIVE));
        Account account = accountDatabaseService.getAccountById(ACCOUNT_ID);
        Assertions.assertEquals(ACCOUNT_ID, account.getId());
        Mockito.verify(accountMapper, times(1)).getAccountById(ACCOUNT_ID);
    }

    @ParameterizedTest
    @EnumSource(Country.class)
    void createAccount(Country country) {
        accountDatabaseService.createAccount(CUSTOMER_ID, country);
        Mockito.verify(accountMapper, times(1)).insertAccount(any());
    }
}