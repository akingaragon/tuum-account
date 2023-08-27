package com.tuum.account.service.db;

import com.tuum.account.entity.Transaction;
import com.tuum.account.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionDatabaseService {
    private final TransactionMapper transactionMapper;

    public void insertTransaction(Transaction transaction) {
        transactionMapper.insertTransaction(transaction);
    }

    public List<Transaction> getAllByAccountId(Long accountId) {
        return transactionMapper.getAllByAccountId(accountId);
    }
}
