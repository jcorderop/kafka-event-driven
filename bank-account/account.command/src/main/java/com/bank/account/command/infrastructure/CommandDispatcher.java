package com.bank.account.command.infrastructure;

import com.bank.cqrs.core.commands.BaseCommand;
import com.bank.cqrs.core.commands.CommandHandlerMethod;
import org.springframework.stereotype.Component;

@Component
public interface CommandDispatcher {

    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);

    void send(BaseCommand command);

}
