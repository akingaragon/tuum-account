package com.tuum.account.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    public static final String QUEUE_NAME_ACCOUNT = "accountQueue";
    public static final String QUEUE_NAME_TRANSACTION = "transactionQueue";

    @Bean
    public Queue accountQueue() {
        return new Queue(QUEUE_NAME_ACCOUNT, false);
    }

    @Bean
    public Queue transactionQueue() {
        return new Queue(QUEUE_NAME_TRANSACTION, false);
    }
}

