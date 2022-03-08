package com.bank.account.command.infrastructure;

import com.bank.account.command.domain.AccountAggregator;
import com.bank.account.command.domain.EventStoreRepository;
import com.bank.cqrs.core.events.BaseEvent;
import com.bank.cqrs.core.events.EventModel;
import com.bank.cqrs.core.exceptions.AggregateNotFoundException;
import com.bank.cqrs.core.exceptions.ConcurrencyException;
import com.bank.cqrs.core.infrastructure.EventStore;
import com.bank.cqrs.core.producers.EventProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccountEventStore implements EventStore {

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private EventStoreRepository eventStoreRepository;

    @Override
    public void saveEvents(String aggregatedId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregatedId);

        if (expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
            throw new ConcurrencyException("Update collision, invalid version.");
        }

        var version = expectedVersion;
        for (var event : events) {
            version++;
            event.setVersion(version);
            var eventModel = EventModel.
                    builder()
                    .timeStamp(LocalDateTime.now())
                    .aggregateIdentifier(aggregatedId)
                    .aggregateType(AccountAggregator.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();

            var persistedEvent  = eventStoreRepository.save(eventModel);
            if (!persistedEvent.getId().isEmpty()) {
                log.info("New Message produced: {} - {}",event.getClass().getSimpleName(), event);
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregatedId) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregatedId);

        if (eventStream ==  null || eventStream.isEmpty()) {
            throw new AggregateNotFoundException("Incorrect Account ID provided.");
        }
        return eventStream.stream().map(EventModel::getEventData).collect(Collectors.toList());
    }

    @Override
    public List<String> getAggregateIds() {
        var eventStream = eventStoreRepository.findAll();

        if (eventStream ==  null || eventStream.isEmpty()) {
            throw new AggregateNotFoundException("Could not retrieve event stream from the DB store.");
        }
        return eventStream.stream()
                .map(EventModel::getAggregateIdentifier)
                .distinct()
                .collect(Collectors.toList());
    }
}
