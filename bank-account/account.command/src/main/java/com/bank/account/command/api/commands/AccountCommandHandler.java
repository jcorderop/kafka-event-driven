package com.bank.account.command.api.commands;

import com.bank.account.command.domain.AccountAggregator;
import com.bank.cqrs.core.handlers.EventSourcingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountCommandHandler implements CommandHandler {

    @Autowired
    private EventSourcingHandler<AccountAggregator> eventSourcingHandler;

    @Override
    public void handler(OpenAccountCommand command) {
        var aggregate = new AccountAggregator(command);
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handler(DepositFundsCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        aggregate.depositFunds(command.getAmount());
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handler(WithdrawFundsCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        aggregate.withdrawFunds(command.getAmount());
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handler(CloseAccountCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        aggregate.closeAccount();
        eventSourcingHandler.save(aggregate);
    }
}
