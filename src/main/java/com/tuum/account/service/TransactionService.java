package com.tuum.account.service;

import com.tuum.account.dto.request.CreateTransactionRequest;
import com.tuum.account.dto.response.CreateTransactionResponseDto;
import com.tuum.account.dto.response.TransactionDto;
import com.tuum.account.entity.Account;
import com.tuum.account.entity.AccountBalance;
import com.tuum.account.entity.Transaction;
import com.tuum.account.enums.Currency;
import com.tuum.account.enums.TransactionDirection;
import com.tuum.account.exception.business.AccountBalanceNotFound;
import com.tuum.account.exception.business.AccountBalanceNotSufficient;
import com.tuum.account.exception.business.UnknownTransactionDirectionException;
import com.tuum.account.mapper.TransactionMapper;
import com.tuum.account.service.db.AccountDatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final AccountDatabaseService accountService;

    private final AccountBalanceManagementService accountBalanceService;
    private final TransactionMapper transactionMapper;

    @Transactional
    public synchronized CreateTransactionResponseDto createTransaction(CreateTransactionRequest createTransactionRequest) {
        Account account = accountService.getAccountById(createTransactionRequest.accountId());
        AccountBalance accountBalance = getAccountBalanceWithValidation(account, createTransactionRequest.currency());

        if (incomingTransaction(createTransactionRequest)) {
            return processIncomingTransaction(createTransactionRequest, account, accountBalance);
        } else if (outgoingTransaction(createTransactionRequest)) {
            return processOutgoingTransaction(createTransactionRequest, account, accountBalance);
        } else {
            throw new UnknownTransactionDirectionException(createTransactionRequest.transactionDirection());
        }
    }

    private AccountBalance getAccountBalanceWithValidation(Account account, Currency currency) {
        AccountBalance accountBalance = accountBalanceService.getAccountBalance(account.getId(), currency);
        if (accountBalance == null) {
            throw new AccountBalanceNotFound(account.getId(), currency);
        }
        return accountBalance;
    }

    private CreateTransactionResponseDto processIncomingTransaction(CreateTransactionRequest createTransactionRequest, Account account, AccountBalance accountBalance) {
        Transaction transaction = createTransaction(createTransactionRequest, account);
        BigDecimal newAvailableAmount = accountBalance.getAvailableAmount().add(createTransactionRequest.amount());
        updateAccountBalance(accountBalance, newAvailableAmount);
        return createTransactionDto(accountBalance, transaction);
    }

    private CreateTransactionResponseDto processOutgoingTransaction(CreateTransactionRequest createTransactionRequest, Account account, AccountBalance accountBalance) {
        if (hasSufficientFunds(createTransactionRequest, accountBalance)) {
            Transaction transaction = createTransaction(createTransactionRequest, account);
            BigDecimal newAvailableAmount = accountBalance.getAvailableAmount().subtract(createTransactionRequest.amount());
            updateAccountBalance(accountBalance, newAvailableAmount);
            return createTransactionDto(accountBalance, transaction);
        } else {
            throw new AccountBalanceNotSufficient(account.getId());
        }
    }

    private void updateAccountBalance(AccountBalance accountBalance, BigDecimal newAvailableAmount) {
        accountBalance.setAvailableAmount(newAvailableAmount);
        accountBalanceService.updateAvailableAmount(accountBalance);
    }


    private boolean outgoingTransaction(CreateTransactionRequest createTransactionRequest) {
        return createTransactionRequest.transactionDirection() == TransactionDirection.OUT;
    }

    private static CreateTransactionResponseDto createTransactionDto(AccountBalance accountBalance, Transaction transaction) {
        return new CreateTransactionResponseDto(createTransactionDto(transaction), accountBalance.getAvailableAmount());
    }

    private static boolean incomingTransaction(CreateTransactionRequest createTransactionRequest) {
        return createTransactionRequest.transactionDirection() == TransactionDirection.IN;
    }

    private Transaction createTransaction(CreateTransactionRequest createTransactionRequest, Account account) {
        Transaction transaction = new Transaction();
        transaction.setAccountId(account.getId());
        transaction.setAmount(createTransactionRequest.amount());
        transaction.setCurrency(createTransactionRequest.currency());
        transaction.setDirection(createTransactionRequest.transactionDirection());
        transaction.setDescription(createTransactionRequest.description());
        transactionMapper.insertTransaction(transaction);
        return transaction;
    }

    private static boolean hasSufficientFunds(CreateTransactionRequest createTransactionRequest, AccountBalance accountBalance) {
        return accountBalance.getAvailableAmount().subtract(createTransactionRequest.amount()).compareTo(BigDecimal.ZERO) >= 0;
    }

    public List<TransactionDto> getTransactionsByAccountId(Long accountId) {
        Account account = accountService.getAccountById(accountId);
        return transactionMapper
                .getAllByAccountId(account.getId())
                .stream()
                .map(TransactionService::createTransactionDto)
                .collect(Collectors.toList());
    }

    private static TransactionDto createTransactionDto(Transaction transaction) {
        return new TransactionDto(transaction.getId(), transaction.getAccountId(), transaction.getAmount(), transaction.getCurrency(), transaction.getDirection(), transaction.getDescription());
    }

}
