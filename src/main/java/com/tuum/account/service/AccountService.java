package com.tuum.account.service;

import com.tuum.account.dto.request.CreateAccountRequest;
import com.tuum.account.dto.response.AccountBalanceDto;
import com.tuum.account.dto.response.AccountDto;
import com.tuum.account.entity.Account;
import com.tuum.account.entity.AccountBalance;
import com.tuum.account.enums.AccountStatus;
import com.tuum.account.exception.business.AccountNotFoundException;
import com.tuum.account.mapper.AccountBalanceMapper;
import com.tuum.account.mapper.AccountMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountMapper accountMapper;

    private final AccountBalanceMapper accountBalanceMapper;

    @Transactional
    public AccountDto createAccount(@Valid CreateAccountRequest createAccountRequest) {
        Account account = createNewAccountEntity(createAccountRequest);

        accountMapper.insertAccount(account);

        insertAccountBalances(createAccountRequest, account);

        List<AccountBalanceDto> accountBalances = createAccountBalanceDtoList(account);

        return createAccountDto(account, accountBalances);
    }

    public AccountDto getAccountDto(Long id) {
        Account account = getAccount(id);
        List<AccountBalanceDto> accountBalances = createAccountBalanceDtoList(account);
        return createAccountDto(account, accountBalances);
    }

    protected Account getAccount(Long id) {
        Account account = accountMapper.getAccountById(id);
        if (account == null) {
            throw new AccountNotFoundException(id);
        }
        return account;
    }

    private static Account createNewAccountEntity(CreateAccountRequest createAccountRequest) {
        return new Account(createAccountRequest.customerId(), createAccountRequest.country(), AccountStatus.ACTIVE);
    }

    private static AccountDto createAccountDto(Account account, List<AccountBalanceDto> accountBalances) {
        return new AccountDto(account.getId(), account.getCustomerId(), accountBalances);
    }

    private List<AccountBalanceDto> createAccountBalanceDtoList(Account account) {
        return accountBalanceMapper
                .getAccountBalancesByAccountId(account.getId())
                .stream()
                .map(accountBalance -> new AccountBalanceDto(accountBalance.getAvailableAmount(), accountBalance.getCurrency())).collect(Collectors.toList());
    }

    private void insertAccountBalances(CreateAccountRequest createAccountRequest, Account account) {
        createAccountRequest
                .currencyList()
                .forEach(currency -> accountBalanceMapper.insertAccountBalance(new AccountBalance(account.getId(), currency)));
    }
}
