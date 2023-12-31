package com.tuum.account.service;

import com.tuum.account.entity.AccountBalance;
import com.tuum.account.enums.Currency;
import com.tuum.account.service.db.AccountBalanceDatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountBalanceManagementService {

    private final AccountBalanceDatabaseService accountBalanceDatabaseService;

    public List<AccountBalance> getAccountBalancesByAccountId(Long accountId) {
        return accountBalanceDatabaseService.getAccountBalances(accountId);
    }

    public AccountBalance createAccountBalance(Long accountId, Currency currency) {
        AccountBalance accountBalance = new AccountBalance(accountId, currency);
        accountBalanceDatabaseService.insertAccountBalance(accountBalance);
        return accountBalance;
    }

    public AccountBalance getAccountBalance(Long accountId, Currency currency) {
        return accountBalanceDatabaseService.getAccountBalancesByAccountIdAndCurrency(accountId, currency);
    }

    public void updateAvailableAmount(AccountBalance accountBalance) {
        accountBalanceDatabaseService.updateAccountBalance(accountBalance);
    }
}
