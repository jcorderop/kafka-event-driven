package com.bank.account.command.infrastructure;

import com.bank.cqrs.core.commands.BaseCommand;
import com.bank.cqrs.core.commands.CommandHandlerMethod;
import com.bank.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {

    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> router = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
        var handlers = router.computeIfAbsent(type, aClass -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public void send(BaseCommand command) {
        var handlers =  router.get(command.getClass());
        if (handlers ==  null || handlers.size() == 0) {
            throw new IllegalArgumentException("No command handler was added.");
        }

        if (handlers.size() > 1) {
            throw new IllegalArgumentException("Cannot send command to more than one handler.");
        }

        handlers.get(0).handler(command);
    }
}
