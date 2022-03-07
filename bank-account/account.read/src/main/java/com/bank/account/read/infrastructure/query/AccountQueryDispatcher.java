package com.bank.account.read.infrastructure.query;

import com.bank.cqrs.core.domain.BaseEntity;
import com.bank.cqrs.core.infrastructure.QueryDispatcher;
import com.bank.cqrs.core.queries.BaseQuery;
import com.bank.cqrs.core.queries.QueryHandlerMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountQueryDispatcher implements QueryDispatcher {

    private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public <U extends BaseEntity> List<U> send(BaseQuery query) {
        var handlers =  routes.get(query.getClass());
        if (handlers == null || handlers.size() <= 0) {
            throw new IllegalStateException("No query handler registered!");
        }
        if (handlers.size() > 1) {
            throw new IllegalStateException("More than one query handler registered!");
        }
        return handlers.get(0).handler(query);
    }

}
