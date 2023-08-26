package com.tuum.account.service;

import com.tuum.account.dto.request.CreateAccountRequest;
import com.tuum.account.dto.response.AccountDto;
import com.tuum.account.entity.Account;
import com.tuum.account.entity.AccountBalance;
import com.tuum.account.enums.AccountStatus;
import com.tuum.account.mapper.AccountBalanceMapper;
import com.tuum.account.mapper.AccountMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountMapper accountMapper;

    private final AccountBalanceMapper accountBalanceMapper;

    @Transactional
    public void createAccount(@Valid CreateAccountRequest createAccountRequest) {
        Account account = new Account(createAccountRequest.customerId(), createAccountRequest.country(), AccountStatus.ACTIVE);

        accountMapper.insertAccount(account);

        createAccountRequest
                .currencyList()
                .forEach(currency -> {
                    accountBalanceMapper.insertAccountBalance(new AccountBalance(account.getId(), currency));
                });

    }
}
