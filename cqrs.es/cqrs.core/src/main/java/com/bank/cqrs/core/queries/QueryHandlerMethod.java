package com.bank.cqrs.core.queries;

import com.bank.cqrs.core.domain.BaseEntity;

import java.util.List;

@FunctionalInterface
public interface QueryHandlerMethod<T extends BaseQuery> {

    List<BaseEntity> handler(T Query);

}
