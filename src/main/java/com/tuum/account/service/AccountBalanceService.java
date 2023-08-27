package com.tuum.account.service;

import com.tuum.account.entity.AccountBalance;
import com.tuum.account.enums.Currency;
import com.tuum.account.mapper.AccountBalanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountBalanceService {

    private final AccountBalanceMapper accountBalanceMapper;

    public List<AccountBalance> getAccountBalancesByAccountId(Long accountId) {
        return accountBalanceMapper.getAccountBalancesByAccountId(accountId);
    }

    public AccountBalance createAccountBalance(Long accountId, Currency currency) {
        AccountBalance accountBalance = new AccountBalance(accountId, currency);
        accountBalanceMapper.insertAccountBalance(accountBalance);
        return accountBalance;
    }

    public AccountBalance getAccountBalance(Long accountId, Currency currency) {
        return accountBalanceMapper.getAccountBalancesByAccountIdAndCurrency(accountId, currency);
    }

    public void updateAvailableAmount(AccountBalance accountBalance) {
        accountBalanceMapper.updateAccountBalance(accountBalance);
    }
}
