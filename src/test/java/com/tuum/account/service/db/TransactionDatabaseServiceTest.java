package com.tuum.account.service.db;

import com.tuum.account.entity.Transaction;
import com.tuum.account.mapper.TransactionMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.tuum.account.util.IntegrationTestHelper.ACCOUNT_ID;

@ExtendWith(MockitoExtension.class)
class TransactionDatabaseServiceTest {

    @InjectMocks
    private TransactionDatabaseService transactionDatabaseService;

    @Mock
    private TransactionMapper transactionMapper;

    @Test
    void insertTransaction() {
        Transaction transaction = new Transaction();
        transactionDatabaseService.insertTransaction(transaction);
        Mockito.verify(transactionMapper, Mockito.times(1)).insertTransaction(transaction);
    }

    @Test
    void getAllByAccountId() {
        transactionDatabaseService.getAllByAccountId(ACCOUNT_ID);
        Mockito.verify(transactionMapper, Mockito.times(1)).getAllByAccountId(ACCOUNT_ID);
    }
}