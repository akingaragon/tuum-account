package com.tuum.account.service.db;

import com.tuum.account.entity.AccountBalance;
import com.tuum.account.enums.Currency;
import com.tuum.account.mapper.AccountBalanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountBalanceDatabaseService {
    private final AccountBalanceMapper accountBalanceMapper;

    public List<AccountBalance> getAccountBalances(Long accountId) {
        return accountBalanceMapper.getAccountBalancesByAccountId(accountId);
    }

    public void insertAccountBalance(AccountBalance accountBalance) {
        accountBalanceMapper.insertAccountBalance(accountBalance);
    }

    public AccountBalance getAccountBalancesByAccountIdAndCurrency(Long accountId, Currency currency) {
        return accountBalanceMapper.getAccountBalancesByAccountIdAndCurrency(accountId, currency);
    }

    public void updateAccountBalance(AccountBalance accountBalance) {
        accountBalanceMapper.updateAccountBalance(accountBalance);
    }
}
