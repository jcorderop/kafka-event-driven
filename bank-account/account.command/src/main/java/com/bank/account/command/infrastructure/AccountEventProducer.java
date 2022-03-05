package com.bank.account.command.infrastructure;

import com.bank.cqrs.core.events.BaseEvent;
import com.bank.cqrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountEventProducer implements EventProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void produce(String topic, BaseEvent baseEvent) {
        this.kafkaTemplate.send(topic, baseEvent);
    }

}
