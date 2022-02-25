package com.bank.account.command.api.commands;

import com.bank.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class DepositFundsCommand extends BaseCommand {

    private double amount;

}
