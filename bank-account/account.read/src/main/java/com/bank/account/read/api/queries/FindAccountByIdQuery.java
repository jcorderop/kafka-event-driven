package com.bank.account.read.api.queries;

import com.bank.cqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountByIdQuery extends BaseQuery {

    private String accountId;

}
