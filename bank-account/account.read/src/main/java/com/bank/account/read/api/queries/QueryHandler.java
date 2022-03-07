package com.bank.account.read.api.queries;

import com.bank.cqrs.core.domain.BaseEntity;

import java.util.List;

public interface QueryHandler {

    List<BaseEntity> handle(FindAllAccountQuery query);

    List<BaseEntity> handle(FindAccountByIdQuery query);

    List<BaseEntity> handle(FindAccountByHolderQuery query);

    List<BaseEntity> handle(FindAccountWithBalanceQuery query);

}

