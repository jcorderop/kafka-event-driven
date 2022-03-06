package com.bank.account.command.api.controllers;

import com.bank.account.command.api.commands.CloseAccountCommand;
import com.bank.account.command.api.commands.DepositFundsCommand;
import com.bank.account.command.api.commands.OpenAccountCommand;
import com.bank.account.command.api.commands.WithdrawFundsCommand;
import com.bank.account.command.api.dto.OpenAccountResponse;
import com.bank.account.common.dto.BaseResponse;
import com.bank.cqrs.core.infrastructure.CommandDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/bank/account")
public class Controller {

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PostMapping(path = "/open")
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        try {
            commandDispatcher.send(command);
            return new ResponseEntity<>(new OpenAccountResponse("Bank account creation request completed successfully!", id), HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            log.warn("Client made a bad request - {}", e.getMessage());
            return new ResponseEntity<>(new BaseResponse(MessageFormat.format("Account could not be created - {0}", e.getMessage())), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error while creating a new account - {}", e.getMessage());
            return new ResponseEntity<>(new BaseResponse(MessageFormat.format("Error while creating a new account - {0}", id)), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/deposit/{id}")
    public ResponseEntity<BaseResponse> deposit(@PathVariable(value = "id") String id,
                                                    @RequestBody DepositFundsCommand command) {
        command.setId(id);
        try {
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("Bank deposit completed successfully!"), HttpStatus.OK);
        } catch (IllegalStateException e) {
            log.warn("Client made a bad request - {}", e.getMessage());
            return new ResponseEntity<>(new BaseResponse(MessageFormat.format("Deposit could not be completed - {0}", e.getMessage())), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error while doing a deposit - {}", e.getMessage());
            return new ResponseEntity<>(new BaseResponse(MessageFormat.format("Error while doing the deposit - {0}", id)), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/withdraw/{id}")
    public ResponseEntity<BaseResponse> withdraw(@PathVariable(value = "id") String id,
                                                    @RequestBody WithdrawFundsCommand command) {
        command.setId(id);
        try {
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("Bank withdraw completed successfully!"), HttpStatus.OK);
        } catch (IllegalStateException e) {
            log.warn("Client made a bad request - {}", e.getMessage());
            return new ResponseEntity<>(new BaseResponse(MessageFormat.format("Withdraw could not be completed - {0}", e.getMessage())), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error while doing a deposit - {}", e.getMessage());
            return new ResponseEntity<>(new BaseResponse(MessageFormat.format("Error while doing the withdraw - {0}", id)), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/close/{id}")
    public ResponseEntity<BaseResponse> closeAccount(@PathVariable(value = "id") String id) {
        CloseAccountCommand command = new CloseAccountCommand(id);
        try {
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("Bank account closed successfully!"), HttpStatus.OK);
        } catch (IllegalStateException e) {
            log.warn("Client made a bad request - {}", e.getMessage());
            return new ResponseEntity<>(new BaseResponse(MessageFormat.format("Account could not be closed - {0}", e.getMessage())), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error while creating a new account - {}", e.getMessage());
            return new ResponseEntity<>(new BaseResponse(MessageFormat.format("Error while closing the account - {0}", id)), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
