package com.finance.Finance.account.dtos;

import com.finance.Finance.enums.CustomEnums;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class AccountDTO {
    private UUID id;
    private UUID customerId;
    private String accountStatus;
    private String accountType;
    private BigDecimal balance;
    private String accountNumber;
    private BigDecimal initialBalance;
    private String currency;
}
