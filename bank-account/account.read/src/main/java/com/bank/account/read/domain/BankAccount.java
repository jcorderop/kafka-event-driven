package com.bank.account.read.domain;

import com.bank.account.common.dto.AccountType;
import com.bank.cqrs.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BankAccount extends BaseEntity {

    @Id
    private String id;
    private String accountHolder;
    private LocalDateTime creationDate;
    private AccountType accountType;
    private double balance;

}
