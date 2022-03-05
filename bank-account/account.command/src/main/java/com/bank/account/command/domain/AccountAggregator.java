package com.bank.account.command.domain;

import com.bank.account.command.api.commands.OpenAccountCommand;
import com.bank.cqrs.core.domain.AggregateRoot;
import events.AccountClosedEvent;
import events.AccountOpenedEvent;
import events.DepositFundsEvent;
import events.WithdrawFundsEvent;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class AccountAggregator extends AggregateRoot {

    private Boolean active;
    private double balance;
    private int version;

    public AccountAggregator(OpenAccountCommand command) {
        raiseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .createDate(LocalDateTime.now())
                .accountType(command.getAccountType())
                .openingBalance(command.getOpeningBalance())
                .build());
    }



    public void apply (AccountOpenedEvent event) {
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositFunds (double amount) {
        if (!this.active) {
            throw new IllegalStateException("Account not active.");
        }
        if (amount <= 0) {
            throw new IllegalStateException("Deposit amount must be greater than zero.");
        }
        raiseEvent(DepositFundsEvent
                .builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply (DepositFundsEvent event) {
        this.id = event.getId();
        this.balance += event.getAmount();
    }



    public void withdrawFunds (double amount) {
        if (!this.active) {
            throw new IllegalStateException("Account not active.");
        }
        if (this.balance - amount <= 0) {
            throw new IllegalStateException("Not enough balance.");
        }
        raiseEvent(WithdrawFundsEvent
                .builder()
                .id(this.id)
                .amount(this.balance - amount)
                .build());
    }

    public void apply (WithdrawFundsEvent event) {
        this.id = event.getId();
        this.balance -= event.getAmount();
    }


    public void closeAccount() {
        if (!this.active) {
            throw new IllegalStateException("Account not active.");
        }
        raiseEvent(AccountClosedEvent
                .builder()
                .id(this.id)
                .build());
    }

    public void apply (AccountClosedEvent event) {
        this.id = event.getId();
        this.active = false;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
