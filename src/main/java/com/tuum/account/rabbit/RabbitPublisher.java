package com.tuum.account.rabbit;


import com.tuum.account.entity.Account;
import com.tuum.account.entity.AccountBalance;
import com.tuum.account.entity.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void sendAccountCreation(Account account) {
        rabbitTemplate.convertAndSend(RabbitConfiguration.QUEUE_NAME_ACCOUNT_CREATED, account);
    }

    public void sendTransactionCreation(Transaction transaction) {
        rabbitTemplate.convertAndSend(RabbitConfiguration.QUEUE_NAME_TRANSACTION_CREATED, transaction);
    }

    public void sendAccountBalanceUpdate(AccountBalance accountBalance) {
        rabbitTemplate.convertAndSend(RabbitConfiguration.QUEUE_NAME_BALANCE_UPDATED, accountBalance);
    }
}

