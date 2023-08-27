package com.tuum.account.service.db;

import com.tuum.account.entity.Transaction;
import com.tuum.account.mapper.TransactionMapper;
import com.tuum.account.rabbit.RabbitPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionDatabaseService {
    private final TransactionMapper transactionMapper;
    private final RabbitPublisher rabbitPublisher;

    public void insertTransaction(Transaction transaction) {
        transactionMapper.insertTransaction(transaction);
        rabbitPublisher.sendTransactionCreation(transaction);
    }

    public List<Transaction> getAllByAccountId(Long accountId) {
        return transactionMapper.getAllByAccountId(accountId);
    }
}
