package com.bank.account.read.api.controlers;

import com.bank.account.read.api.dto.AccountLookupResponse;
import com.bank.account.read.api.dto.EqualityType;
import com.bank.account.read.api.queries.FindAccountByHolderQuery;
import com.bank.account.read.api.queries.FindAccountByIdQuery;
import com.bank.account.read.api.queries.FindAccountWithBalanceQuery;
import com.bank.account.read.api.queries.FindAllAccountQuery;
import com.bank.account.read.domain.BankAccount;
import com.bank.cqrs.core.infrastructure.QueryDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/bank/account")
public class AccountLookupController {

    @Autowired
    private QueryDispatcher queryDispatcher;

    @GetMapping(path = "/all")
    public ResponseEntity<AccountLookupResponse> getAllAccounts() {
        try {
            final List<BankAccount> accounts = queryDispatcher.send(new FindAllAccountQuery());
            if (accounts == null || accounts.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            final var response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Successfully returned {0} bank accounts.", accounts.size()))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            final var safeErrorMessage = "Could not complete the get command!";
            log.error("{} - {}",safeErrorMessage, e.getMessage());
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/number/{id}")
    public ResponseEntity<AccountLookupResponse> getAccountsById(@PathVariable(value = "id") String id) {
        try {
            final List<BankAccount> accounts = queryDispatcher.send(new FindAccountByIdQuery(id));
            if (accounts == null || accounts.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            final var response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Successfully returned {0} bank accounts.", accounts.size()))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            final var safeErrorMessage = "Could not complete the get command!";
            log.error("{} - {}",safeErrorMessage, e.getMessage());
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/holder/{holder}")
    public ResponseEntity<AccountLookupResponse> getAccountsByHolder(@PathVariable(value = "holder") String holder) {
        try {
            final List<BankAccount> accounts = queryDispatcher.send(new FindAccountByHolderQuery(holder));
            if (accounts == null || accounts.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            final var response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Successfully returned {0} bank accounts.", accounts.size()))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            final var safeErrorMessage = "Could not complete the get command!";
            log.error("{} - {}",safeErrorMessage, e.getMessage());
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/balance/{equalityType}/{balance}")
    public ResponseEntity<AccountLookupResponse> getAccountsWithBalance(@PathVariable(value = "equalityType") EqualityType equalityType,
                                                                      @PathVariable(value = "balance") Double balance) {
        try {
            final List<BankAccount> accounts = queryDispatcher.send(new FindAccountWithBalanceQuery(equalityType, balance));
            if (accounts == null || accounts.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            final var response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Successfully returned {0} bank accounts.", accounts.size()))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            final var safeErrorMessage = "Could not complete the get command!";
            log.error("{} - {}",safeErrorMessage, e.getMessage());
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
