package com.bank.cqrs.core.infrastructure;

import com.bank.cqrs.core.events.BaseEvent;

import java.util.List;

public interface EventStore {

    void saveEvents(String aggregatedId, Iterable<BaseEvent> events, int expectedVersion);

    List<BaseEvent> getEvents(String aggregatedId);

    List<String> getAggregateIds();
}
