package events;

import com.bank.account.common.dto.AccountType;
import com.bank.cqrs.core.events.BaseEvent;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AccountOpenedEvent extends BaseEvent {

    private String accountHolder;
    private AccountType accountType;
    private LocalDate createDate;
    private double openingBalance;

}
