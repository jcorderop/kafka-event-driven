package com.bank.account.read.api.queries;

import com.bank.account.read.api.dto.EqualityType;
import com.bank.cqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountWithBalanceQuery extends BaseQuery {

    private EqualityType equalityType;

    private double balance;

}
