package com.tuum.account.rabbit;

import com.tuum.account.entity.Account;
import com.tuum.account.entity.AccountBalance;
import com.tuum.account.entity.Transaction;
import com.tuum.account.enums.AccountStatus;
import com.tuum.account.enums.Country;
import com.tuum.account.enums.Currency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static com.tuum.account.util.IntegrationTestHelper.ACCOUNT_ID;
import static com.tuum.account.util.IntegrationTestHelper.CUSTOMER_ID;

@ExtendWith(MockitoExtension.class)
class RabbitPublisherTest {

    @InjectMocks
    private RabbitPublisher rabbitPublisher;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Test
    void sendAccountCreation() {
        Account account = new Account(ACCOUNT_ID, CUSTOMER_ID, Country.EE, AccountStatus.ACTIVE);
        rabbitPublisher.sendAccountCreation(account);
        Mockito.verify(rabbitTemplate, Mockito.times(1)).convertAndSend(RabbitConfiguration.QUEUE_NAME_ACCOUNT_CREATED, account);
    }

    @Test
    void sendTransactionCreation() {
        Transaction transaction = new Transaction();
        rabbitPublisher.sendTransactionCreation(transaction);
        Mockito.verify(rabbitTemplate, Mockito.times(1)).convertAndSend(RabbitConfiguration.QUEUE_NAME_TRANSACTION_CREATED, transaction);
    }

    @Test
    void sendAccountBalanceUpdate() {
        AccountBalance accountBalance = new AccountBalance(ACCOUNT_ID, Currency.EUR);
        rabbitPublisher.sendAccountBalanceUpdate(accountBalance);
        Mockito.verify(rabbitTemplate, Mockito.times(1)).convertAndSend(RabbitConfiguration.QUEUE_NAME_BALANCE_UPDATED, accountBalance);
    }
}