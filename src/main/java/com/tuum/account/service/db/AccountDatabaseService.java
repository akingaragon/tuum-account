package com.tuum.account.service.db;

import com.tuum.account.entity.Account;
import com.tuum.account.enums.AccountStatus;
import com.tuum.account.enums.Country;
import com.tuum.account.exception.business.AccountNotFoundException;
import com.tuum.account.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountDatabaseService {

    private final AccountMapper accountMapper;

    public Account createAccount(Long customerId, Country country) {
        Account account = Account.builder()
                .customerId(customerId)
                .country(country)
                .status(AccountStatus.ACTIVE)
                .build();
        accountMapper.insertAccount(account);
        return account;
    }

    public Account getAccountById(Long id) {
        Account account = accountMapper.getAccountById(id);
        if (account == null) {
            throw new AccountNotFoundException(id);
        }
        return account;
    }


}
