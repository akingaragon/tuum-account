package com.tuum.account.service;

import com.tuum.account.dto.request.CreateTransactionRequest;
import com.tuum.account.dto.response.AccountBalanceDto;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static com.tuum.account.util.IntegrationTestHelper.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionManagementServiceTest {

    @InjectMocks
    private TransactionManagementService transactionManagementService;

    @Mock
    private AccountManagementService accountManagementService;

    @Mock
    private TransactionDatabaseService transactionDatabaseService;

    @Mock
    private AccountBalanceManagementService accountBalanceManagementService;

    @ParameterizedTest
    @EnumSource(Currency.class)
    void createTransactionInDirection(Currency currency) {
        AccountBalanceDto accountBalanceDto = new AccountBalanceDto(BigDecimal.TEN, currency);
        AccountBalance accountBalance = new AccountBalance(ACCOUNT_ID, currency);
        accountBalance.setAvailableAmount(BigDecimal.TEN);
        AccountDto accountDto = new AccountDto(ACCOUNT_ID, CUSTOMER_ID, List.of(accountBalanceDto));

        when(accountManagementService.getAccount(ACCOUNT_ID)).thenReturn(accountDto);
        when(accountBalanceManagementService.getAccountBalance(ACCOUNT_ID, currency)).thenReturn(accountBalance);

        CreateTransactionRequest createTransactionRequest = new CreateTransactionRequest(ACCOUNT_ID, BigDecimal.TEN, currency, TransactionDirection.IN, "Description");
        CreateTransactionResponseDto transaction = transactionManagementService.createTransaction(createTransactionRequest);
        Assertions.assertEquals(new BigDecimal(20), transaction.balanceAfterTransaction());
        Mockito.verify(transactionDatabaseService, Mockito.times(1)).insertTransaction(any());
        Mockito.verify(accountBalanceManagementService, Mockito.times(1)).updateAvailableAmount(any());
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    void createTransactionOutDirectionSuccess(Currency currency) {
        AccountBalanceDto accountBalanceDto = new AccountBalanceDto(BigDecimal.TEN, currency);
        AccountBalance accountBalance = new AccountBalance(ACCOUNT_ID, currency);
        accountBalance.setAvailableAmount(BigDecimal.TEN);
        AccountDto accountDto = new AccountDto(ACCOUNT_ID, CUSTOMER_ID, List.of(accountBalanceDto));

        when(accountManagementService.getAccount(ACCOUNT_ID)).thenReturn(accountDto);
        when(accountBalanceManagementService.getAccountBalance(ACCOUNT_ID, currency)).thenReturn(accountBalance);

        CreateTransactionRequest createTransactionRequest = new CreateTransactionRequest(ACCOUNT_ID, new BigDecimal(9), currency, TransactionDirection.OUT, "Description");
        CreateTransactionResponseDto transaction = transactionManagementService.createTransaction(createTransactionRequest);
        Assertions.assertEquals(BigDecimal.ONE, transaction.balanceAfterTransaction());
        Mockito.verify(transactionDatabaseService, Mockito.times(1)).insertTransaction(any());
        Mockito.verify(accountBalanceManagementService, Mockito.times(1)).updateAvailableAmount(any());
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    void createTransactionOutDirectionNotSufficientFund(Currency currency) {
        AccountBalanceDto accountBalanceDto = new AccountBalanceDto(BigDecimal.TEN, currency);
        AccountBalance accountBalance = new AccountBalance(ACCOUNT_ID, currency);
        accountBalance.setAvailableAmount(BigDecimal.ONE);
        AccountDto accountDto = new AccountDto(ACCOUNT_ID, CUSTOMER_ID, List.of(accountBalanceDto));

        when(accountManagementService.getAccount(ACCOUNT_ID)).thenReturn(accountDto);
        when(accountBalanceManagementService.getAccountBalance(ACCOUNT_ID, currency)).thenReturn(accountBalance);

        CreateTransactionRequest createTransactionRequest = new CreateTransactionRequest(ACCOUNT_ID, BigDecimal.TEN, currency, TransactionDirection.OUT, "Description");

        Assertions.assertThrows(AccountBalanceNotSufficient.class, () -> transactionManagementService.createTransaction(createTransactionRequest));
        Mockito.verifyNoInteractions(transactionDatabaseService);
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    void getTransactionsByAccountId(Currency currency) {
        AccountBalanceDto accountBalanceDto = new AccountBalanceDto(BigDecimal.TEN, currency);
        AccountDto accountDto = new AccountDto(ACCOUNT_ID, CUSTOMER_ID, List.of(accountBalanceDto));
        when(accountManagementService.getAccount(ACCOUNT_ID)).thenReturn(accountDto);
        List<Transaction> transactionList = List.of(
                new Transaction(TRANSACTION_ID, ACCOUNT_ID, BigDecimal.TEN, currency, TransactionDirection.IN, "description in"),
                new Transaction(TRANSACTION_ID, ACCOUNT_ID, BigDecimal.ONE, currency, TransactionDirection.OUT, "description out"));

        when(transactionDatabaseService.getAllByAccountId(ACCOUNT_ID)).thenReturn(transactionList);

        List<TransactionDto> transactionsByAccountId = transactionManagementService.getTransactionsByAccountId(ACCOUNT_ID);
        Assertions.assertEquals(2, transactionsByAccountId.size());
        Assertions.assertEquals(transactionList.get(0).getDirection(), transactionsByAccountId.get(0).transactionDirection());
        Assertions.assertEquals(transactionList.get(0).getAccountId(), transactionsByAccountId.get(0).accountId());
        Assertions.assertEquals(transactionList.get(0).getAmount(), transactionsByAccountId.get(0).amount());
        Assertions.assertEquals(transactionList.get(0).getDescription(), transactionsByAccountId.get(0).description());

    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    void accountBalanceNotFoundExceptionTest(Currency currency) {
        AccountBalanceDto accountBalanceDto = new AccountBalanceDto(BigDecimal.TEN, currency);
        AccountDto accountDto = new AccountDto(ACCOUNT_ID, CUSTOMER_ID, List.of(accountBalanceDto));

        when(accountManagementService.getAccount(ACCOUNT_ID)).thenReturn(accountDto);
        when(accountBalanceManagementService.getAccountBalance(ACCOUNT_ID, currency)).thenReturn(null);

        CreateTransactionRequest createTransactionRequest = new CreateTransactionRequest(ACCOUNT_ID, BigDecimal.TEN, currency, TransactionDirection.IN, "Description");

        Assertions.assertThrows(AccountBalanceNotFound.class, () -> transactionManagementService.createTransaction(createTransactionRequest));
        Mockito.verifyNoInteractions(transactionDatabaseService);
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    void unknownTransactionDirectionExceptionTest(Currency currency) {
        AccountBalanceDto accountBalanceDto = new AccountBalanceDto(BigDecimal.TEN, currency);
        AccountBalance accountBalance = new AccountBalance(ACCOUNT_ID, currency);
        accountBalance.setAvailableAmount(BigDecimal.TEN);
        AccountDto accountDto = new AccountDto(ACCOUNT_ID, CUSTOMER_ID, List.of(accountBalanceDto));

        when(accountManagementService.getAccount(ACCOUNT_ID)).thenReturn(accountDto);
        when(accountBalanceManagementService.getAccountBalance(ACCOUNT_ID, currency)).thenReturn(accountBalance);

        CreateTransactionRequest createTransactionRequest = new CreateTransactionRequest(ACCOUNT_ID, BigDecimal.TEN, currency, null, "Description");

        Assertions.assertThrows(UnknownTransactionDirectionException.class, () -> transactionManagementService.createTransaction(createTransactionRequest));
        Mockito.verifyNoInteractions(transactionDatabaseService);
    }

}