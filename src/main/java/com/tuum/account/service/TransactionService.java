package com.tuum.account.service;

import com.tuum.account.dto.request.CreateTransactionRequest;
import com.tuum.account.dto.response.TransactionDto;
import com.tuum.account.entity.Account;
import com.tuum.account.entity.Transaction;
import com.tuum.account.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final AccountService accountService;
    private final TransactionMapper transactionMapper;

    @Transactional
    public synchronized TransactionDto createTransaction(CreateTransactionRequest createTransactionRequest) {
        Account account = accountService.getAccountById(createTransactionRequest.accountId());

        //if(createTransactionRequest.transactionDirection()== TransactionDirection.IN){
        Transaction transaction = new Transaction();
        transaction.setAccountId(account.getId());
        transaction.setAmount(createTransactionRequest.amount());
        transaction.setCurrency(createTransactionRequest.currency());
        transaction.setDirection(createTransactionRequest.transactionDirection());
        transaction.setDescription(createTransactionRequest.description());
        transactionMapper.insertTransaction(transaction);

        //}


        BigDecimal balanceAfterTransaction=BigDecimal.ZERO;
        return new TransactionDto(transaction.getId(), account.getId(), transaction.getAmount(), transaction.getCurrency(), transaction.getDirection(), transaction.getDescription(), balanceAfterTransaction);
    }
}
