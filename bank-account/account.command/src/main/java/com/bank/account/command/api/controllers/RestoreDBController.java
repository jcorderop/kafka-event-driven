package com.bank.account.command.api.controllers;

import com.bank.account.command.api.commands.RestoreReadDBCommand;
import com.bank.account.common.dto.BaseResponse;
import com.bank.cqrs.core.infrastructure.CommandDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/restore/db")
public class RestoreDBController {

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> openAccount() {
        try {
            commandDispatcher.send(new RestoreReadDBCommand());
            return new ResponseEntity<>(new BaseResponse("Database restored successfully!"), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Database could not be restored - {}", e.getMessage());
            return new ResponseEntity<>(new BaseResponse("Database could not be restored"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
