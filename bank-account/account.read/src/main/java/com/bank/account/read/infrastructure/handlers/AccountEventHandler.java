package com.bank.account.read.infrastructure.handlers;

import com.bank.account.read.domain.AccountRepository;
import com.bank.account.read.domain.BankAccount;
import events.AccountClosedEvent;
import events.AccountOpenedEvent;
import events.DepositFundsEvent;
import events.WithdrawFundsEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountEventHandler implements EventHandler {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void on(final AccountOpenedEvent event) {
        final var bankAccount = BankAccount.builder()
                .id(event.getId())
                .accountHolder(event.getAccountHolder())
                .creationDate(event.getCreateDate())
                .accountType(event.getAccountType())
                .balance(event.getOpeningBalance())
                .build();
        accountRepository.save(bankAccount);
    }

    @Override
    public void on(final DepositFundsEvent event) {
        final var bankAccount = accountRepository.findById(event.getId());
        if (bankAccount.isEmpty()) {
            return;
        }

        final var currentValance = bankAccount.get().getBalance();
        final var latestBalance = currentValance + event.getAmount();
        bankAccount.get().setBalance(latestBalance);
        accountRepository.save(bankAccount.get());
    }

    @Override
    public void on(final WithdrawFundsEvent event) {
        final var bankAccount = accountRepository.findById(event.getId());
        if (bankAccount.isEmpty()) {
            return;
        }

        final var currentValance = bankAccount.get().getBalance();
        final var latestBalance = currentValance - event.getAmount();
        bankAccount.get().setBalance(latestBalance);
        accountRepository.save(bankAccount.get());
    }

    @Override
    public void on(final AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());
    }
}
