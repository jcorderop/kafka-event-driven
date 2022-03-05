package com.bank.account.read.infrastructure.handlers;

import events.AccountClosedEvent;
import events.AccountOpenedEvent;
import events.DepositFundsEvent;
import events.WithdrawFundsEvent;

public interface EventHandler {

    void on(AccountOpenedEvent event);

    void on(DepositFundsEvent event);

    void on(WithdrawFundsEvent event);

    void on(AccountClosedEvent event);

}
