package com.bank.account.read.infrastructure.consumers;

import com.bank.account.read.infrastructure.handlers.EventHandler;
import events.AccountClosedEvent;
import events.AccountOpenedEvent;
import events.DepositFundsEvent;
import events.WithdrawFundsEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccountEventConsumer implements EventConsumer {

    @Autowired
    private EventHandler eventHandler;

    @KafkaListener(topics = "AccountOpenedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(AccountOpenedEvent event, Acknowledgment ack) {
        log.info("New Event arrived - {}", event);
        eventHandler.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "DepositFundsEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(DepositFundsEvent event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "WithdrawFundsEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(WithdrawFundsEvent event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "AccountClosedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(AccountClosedEvent event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }
}
