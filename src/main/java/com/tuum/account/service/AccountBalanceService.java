package com.tuum.account.service;

import com.tuum.account.entity.Account;
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

    public List<AccountBalance> getAccountBalancesByAccountId(Account account) {
        return accountBalanceMapper.getAccountBalancesByAccountId(account.getId());
    }

    public void createAccountBalance(AccountBalance accountBalance) {
        accountBalanceMapper.insertAccountBalance(accountBalance);
    }

    public AccountBalance getAccountBalance(Long id, Currency currency) {
        return accountBalanceMapper.getAccountBalancesByAccountIdAndCurrency(id, currency);
    }

    public void updateAvailableAmount(AccountBalance accountBalance) {
        accountBalanceMapper.updateAccountBalance(accountBalance);
    }
}
