package com.bank.account.read.infrastructure.consumers;

import events.AccountClosedEvent;
import events.AccountOpenedEvent;
import events.DepositFundsEvent;
import events.WithdrawFundsEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {

    void consume(@Payload AccountOpenedEvent event, Acknowledgment ack);

    void consume(@Payload DepositFundsEvent event, Acknowledgment ack);

    void consume(@Payload WithdrawFundsEvent event, Acknowledgment ack);

    void consume(@Payload AccountClosedEvent event, Acknowledgment ack);

}
