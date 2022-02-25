package com.bank.account.command;

import com.bank.account.command.api.commands.*;
import com.bank.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class AccountCommandApplication {

	@Autowired
	private CommandDispatcher commandDispatcher;

	@Autowired
	private CommandHandler commandHandler;

	public static void main(String[] args) {
		SpringApplication.run(AccountCommandApplication.class, args);
	}

	@PostConstruct
	public void registerHandler () {
		commandDispatcher.registerHandler(OpenAccountCommand.class, commandHandler::handler);
		commandDispatcher.registerHandler(DepositFundsCommand.class, commandHandler::handler);
		commandDispatcher.registerHandler(WithdrawFundsCommand.class, commandHandler::handler);
		commandDispatcher.registerHandler(CloseAccountCommand.class, commandHandler::handler);
	}

}
