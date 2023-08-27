package com.tuum.account.service;

import com.tuum.account.service.db.AccountBalanceDatabaseService;
import com.tuum.account.service.db.AccountDatabaseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountBalanceManagementServiceTest {

    @InjectMocks
    private AccountBalanceManagementService accountBalanceManagementService;

    @Mock
    private AccountBalanceDatabaseService accountBalanceDatabaseService;

    @Mock
    private AccountBalanceManagementService accountBalanceService;


    @Test
    void getAccountBalancesByAccountId() {
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