package com.bank.account.command.infrastructure;

import com.bank.account.command.domain.AccountAggregator;
import com.bank.cqrs.core.domain.AggregateRoot;
import com.bank.cqrs.core.handlers.EventSourcingHandler;
import com.bank.cqrs.core.infrastructure.EventStore;
import com.bank.cqrs.core.producers.EventProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Slf4j
@Service
public class AccountEventSourcingHandler implements EventSourcingHandler {

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private EventStore eventStore;

    @Override
    public void save(AggregateRoot aggregate) {
        log.info("{}",aggregate);
        eventStore.saveEvents(aggregate.getId(),
                aggregate.getUncommittedChanges(),
                aggregate.getVersion());

        aggregate.makeChangesAsCommitted();
    }

    @Override
    public AccountAggregator getById(String id) {
        var aggregate = new AccountAggregator();
        var events = eventStore.getEvents(id);

        if (events != null && !events.isEmpty()) {
            aggregate.replyEvents(events);
            var latestVersion = events.
                    stream().
                    map(event -> event.getVersion()).max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }
        return aggregate;
    }

    @Override
    public void republishEvents() {
        var aggregateIds = eventStore.getAggregateIds();
        for (var aggregateId : aggregateIds) {
            var aggregate = getById(aggregateId);
            if (aggregate == null || !aggregate.getActive()) {
                continue;
            }
            var events = eventStore.getEvents(aggregateId);
            for (var event : events) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }
}
