package com.tuum.account.service;

import com.tuum.account.dto.request.CreateTransactionRequest;
import com.tuum.account.dto.response.AccountDto;
import com.tuum.account.dto.response.CreateTransactionResponseDto;
import com.tuum.account.dto.response.TransactionDto;
import com.tuum.account.entity.AccountBalance;
import com.tuum.account.entity.Transaction;
import com.tuum.account.enums.Currency;
import com.tuum.account.enums.TransactionDirection;
import com.tuum.account.exception.business.AccountBalanceNotFound;
import com.tuum.account.exception.business.AccountBalanceNotSufficient;
import com.tuum.account.exception.business.UnknownTransactionDirectionException;
import com.tuum.account.service.db.TransactionDatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionManagementService {
    private final AccountManagementService accountManagementService;

    private final AccountBalanceManagementService accountBalanceManagementService;

    private final TransactionDatabaseService transactionDatabaseService;

    @Transactional
    public synchronized CreateTransactionResponseDto createTransaction(CreateTransactionRequest createTransactionRequest) {
        AccountDto accountDto = accountManagementService.getAccount(createTransactionRequest.accountId());
        AccountBalance accountBalance = getAccountBalanceWithValidation(accountDto, createTransactionRequest.currency());

        if (incomingTransaction(createTransactionRequest)) {
            return processIncomingTransaction(createTransactionRequest, accountDto, accountBalance);
        } else if (outgoingTransaction(createTransactionRequest)) {
            return processOutgoingTransaction(createTransactionRequest, accountDto, accountBalance);
        } else {
            throw new UnknownTransactionDirectionException(createTransactionRequest.transactionDirection());
        }
    }

    private AccountBalance getAccountBalanceWithValidation(AccountDto accountDto, Currency currency) {
        AccountBalance accountBalance = accountBalanceManagementService.getAccountBalance(accountDto.accountId(), currency);
        if (accountBalance == null) {
            throw new AccountBalanceNotFound(accountDto.accountId(), currency);
        }
        return accountBalance;
    }

    private CreateTransactionResponseDto processIncomingTransaction(CreateTransactionRequest createTransactionRequest, AccountDto accountDto, AccountBalance accountBalance) {
        Transaction transaction = createTransaction(createTransactionRequest, accountDto);
        BigDecimal newAvailableAmount = accountBalance.getAvailableAmount().add(createTransactionRequest.amount());
        updateAccountBalance(accountBalance, newAvailableAmount);
        return createTransactionDto(accountBalance, transaction);
    }

    private CreateTransactionResponseDto processOutgoingTransaction(CreateTransactionRequest createTransactionRequest, AccountDto accountDto, AccountBalance accountBalance) {
        if (hasSufficientFunds(createTransactionRequest, accountBalance)) {
            Transaction transaction = createTransaction(createTransactionRequest, accountDto);
            BigDecimal newAvailableAmount = accountBalance.getAvailableAmount().subtract(createTransactionRequest.amount());
            updateAccountBalance(accountBalance, newAvailableAmount);
            return createTransactionDto(accountBalance, transaction);
        } else {
            throw new AccountBalanceNotSufficient(accountDto.accountId());
        }
    }

    private void updateAccountBalance(AccountBalance accountBalance, BigDecimal newAvailableAmount) {
        accountBalance.setAvailableAmount(newAvailableAmount);
        accountBalanceManagementService.updateAvailableAmount(accountBalance);
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

    private Transaction createTransaction(CreateTransactionRequest createTransactionRequest, AccountDto accountDto) {
        Transaction transaction = new Transaction();
        transaction.setAccountId(accountDto.accountId());
        transaction.setAmount(createTransactionRequest.amount());
        transaction.setCurrency(createTransactionRequest.currency());
        transaction.setDirection(createTransactionRequest.transactionDirection());
        transaction.setDescription(createTransactionRequest.description());
        transactionDatabaseService.insertTransaction(transaction);
        return transaction;
    }

    private static boolean hasSufficientFunds(CreateTransactionRequest createTransactionRequest, AccountBalance accountBalance) {
        return accountBalance.getAvailableAmount().subtract(createTransactionRequest.amount()).compareTo(BigDecimal.ZERO) >= 0;
    }

    public List<TransactionDto> getTransactionsByAccountId(Long accountId) {
        AccountDto accountDto = accountManagementService.getAccount(accountId);
        return transactionDatabaseService
                .getAllByAccountId(accountDto.accountId())
                .stream()
                .map(TransactionManagementService::createTransactionDto)
                .collect(Collectors.toList());
    }

    private static TransactionDto createTransactionDto(Transaction transaction) {
        return new TransactionDto(transaction.getId(), transaction.getAccountId(), transaction.getAmount(), transaction.getCurrency(), transaction.getDirection(), transaction.getDescription());
    }

}
