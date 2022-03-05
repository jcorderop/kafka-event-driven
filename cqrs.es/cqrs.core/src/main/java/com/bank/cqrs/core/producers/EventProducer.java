package com.bank.cqrs.core.producers;

import com.bank.cqrs.core.events.BaseEvent;

public interface EventProducer {

    void produce(String topic, BaseEvent baseEvent);

}
