package com.bank.account.command.api.commands;

import com.bank.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class CloseAccountCommand extends BaseCommand {

    public CloseAccountCommand (String id) {
        super(id);
    }

}
