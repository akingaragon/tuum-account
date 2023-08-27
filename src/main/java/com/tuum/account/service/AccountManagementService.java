package com.tuum.account.service;

import com.tuum.account.dto.request.CreateAccountRequest;
import com.tuum.account.dto.response.AccountBalanceDto;
import com.tuum.account.dto.response.AccountDto;
import com.tuum.account.entity.Account;
import com.tuum.account.entity.AccountBalance;
import com.tuum.account.enums.Currency;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountManagementService {

    private final AccountService accountService;
    private final AccountBalanceService accountBalanceService;

    @Transactional
    public AccountDto createAccountWithInitialBalances(@Valid CreateAccountRequest request) {

        Account account = accountService.createAccount(request.customerId(), request.country());

        List<AccountBalance> accountBalances = new ArrayList<>();
        for (Currency currency : request.currencyList()) {
            AccountBalance accountBalance = accountBalanceService.createAccountBalance(account.getId(), currency);
            accountBalances.add(accountBalance);
        }
        return createAccountDtoWithBalances(account, accountBalances);
    }

    public AccountDto getAccount(Long id) {
        Account account = accountService.getAccountById(id);
        List<AccountBalance> accountBalances = accountBalanceService.getAccountBalancesByAccountId(account.getId());

        return createAccountDtoWithBalances(account, accountBalances);
    }

    private static AccountDto createAccountDtoWithBalances(Account account, List<AccountBalance> accountBalances) {
        return new AccountDto(account.getId(), account.getCustomerId(), getBalanceDtoList(accountBalances));
    }

    private static List<AccountBalanceDto> getBalanceDtoList(List<AccountBalance> accountBalances) {
        return accountBalances
                .stream()
                .map(accountBalance -> new AccountBalanceDto(accountBalance.getAvailableAmount(), accountBalance.getCurrency())).collect(Collectors.toList());
    }
}
