package com.tuum.account.rabbit;


import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void sendAccountUpdate() {
        rabbitTemplate.convertAndSend(RabbitConfiguration.QUEUE_NAME_ACCOUNT, "TODO account update object");
    }

    public void sendTransactionUpdate() {
        rabbitTemplate.convertAndSend(RabbitConfiguration.QUEUE_NAME_TRANSACTION, "TODO transaction update object");
    }
}

