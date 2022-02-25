package com.bank.cqrs.core.domain;

import com.bank.cqrs.core.events.BaseEvent;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
public abstract class AggregateRoot {

    protected String id;
    private int version = -1;

    @ToString.Exclude
    private final List<BaseEvent> changes = new ArrayList<>();

    public List<BaseEvent> getUncommittedChanges() {
        return changes;
    }

    public void makeChangesAsCommitted() {
        this.changes.clear();
    }

    protected void applyChange(BaseEvent event, Boolean inNewEvent) {
        try {
            var method = getClass().getDeclaredMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        } catch (NoSuchMethodException e) {
            log.error("The apply method was not found: {0}", event.getClass().getName());
        } catch (Exception e) {
            log.error("Error apply method: {0}", event.getClass().getName());
            log.error(e.getMessage());
        } finally {
            if (inNewEvent) {
                changes.add(event);
            }
        }
    }

    public void raiseEvent(BaseEvent event) {
        applyChange(event, true);
    }

    public void replyEvents(Iterable<BaseEvent> events) {
        events.forEach(event -> applyChange(event, false));
    }
}
