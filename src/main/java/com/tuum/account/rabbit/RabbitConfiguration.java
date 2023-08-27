package com.tuum.account.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    public static final String QUEUE_NAME_BALANCE_UPDATED = "balanceUpdatedQueue";

    public static final String QUEUE_NAME_ACCOUNT_CREATED = "accountCreatedQueue";
    public static final String QUEUE_NAME_TRANSACTION_CREATED = "transactionCreatedQueue";

    @Bean
    public Queue accountQueue() {
        return new Queue(QUEUE_NAME_ACCOUNT_CREATED, false);
    }

    @Bean
    public Queue transactionQueue() {
        return new Queue(QUEUE_NAME_TRANSACTION_CREATED, false);
    }

    @Bean
    public Queue balanceUpdateQueue() {
        return new Queue(QUEUE_NAME_BALANCE_UPDATED, false);
    }
}

