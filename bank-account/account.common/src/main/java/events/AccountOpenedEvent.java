package events;

import com.bank.account.common.dto.AccountType;
import com.bank.cqrs.core.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AccountOpenedEvent extends BaseEvent {

    private String accountHolder;
    private AccountType accountType;
    private LocalDateTime createDate;
    private double openingBalance;

}
