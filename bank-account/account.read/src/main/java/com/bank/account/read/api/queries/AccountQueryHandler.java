package com.bank.account.read.api.queries;

import com.bank.account.read.api.dto.EqualityType;
import com.bank.account.read.domain.AccountRepository;
import com.bank.account.read.domain.BankAccount;
import com.bank.cqrs.core.domain.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountQueryHandler implements QueryHandler {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<BaseEntity> handle(FindAllAccountQuery query) {
        Iterable<BankAccount> bankAccounts = accountRepository.findAll();
        List<BaseEntity> baseEntityList = new ArrayList<>();
        bankAccounts.forEach(baseEntityList::add);
        return baseEntityList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByIdQuery query) {
        Optional<BankAccount> bankAccounts = accountRepository.findById(query.getAccountId());
        if (bankAccounts.isEmpty()) {
            return null;
        }
        List<BaseEntity> baseEntityList = new ArrayList<>();
        baseEntityList.add(bankAccounts.get());
        return baseEntityList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByHolderQuery query) {
        Optional<BankAccount> bankAccounts = accountRepository.findByAccountHolder(query.getHolder());
        if (bankAccounts.isEmpty()) {
            return null;
        }
        List<BaseEntity> baseEntityList = new ArrayList<>();
        baseEntityList.add(bankAccounts.get());
        return baseEntityList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountWithBalanceQuery query) {
        List<BaseEntity> baseEntityList = query.getEqualityType().equals(EqualityType.GREATER_THAN) ?
                accountRepository.findByBalanceGreaterThan(query.getBalance()) :
                accountRepository.findByBalanceLessThan(query.getBalance());
        return baseEntityList;
    }
}
