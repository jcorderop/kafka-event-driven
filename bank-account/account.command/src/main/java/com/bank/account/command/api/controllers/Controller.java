package com.bank.account.command.api.controllers;

import com.bank.account.command.api.commands.OpenAccountCommand;
import com.bank.account.command.api.dto.OpenAccountResponse;
import com.bank.account.common.dto.BaseResponse;
import com.bank.cqrs.core.infrastructure.CommandDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/openBankAccount")
public class Controller {

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PostMapping
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
}
