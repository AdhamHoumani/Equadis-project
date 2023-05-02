package com.finance.Finance.transaction.dtos;

import com.finance.Finance.account.models.Account;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
public class TransactionDTO {
    private UUID id;
    private UUID accountId;
    private String transactionStatus;
    private String transactionType;
    private BigDecimal amount;
    private Date createdOn;
}
