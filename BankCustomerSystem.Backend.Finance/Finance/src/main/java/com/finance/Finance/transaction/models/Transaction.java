package com.finance.Finance.transaction.models;

import com.finance.Finance.account.models.Account;
import com.finance.Finance.enums.CustomEnums;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(generator = "uuid2")
    @Type(type = "pg-uuid")
    private UUID id;

    @NotNull(message = "{cannot.be.null.key}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @NotNull(message = "{cannot.be.null.key}")
    @Column(name = "transaction_status", columnDefinition = "TEXT", nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomEnums.TransactionStatus transactionStatus;

    @NotNull(message = "{cannot.be.null.key}")
    @Column(name = "transaction_type", columnDefinition = "TEXT", nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomEnums.TransactionType transactionType;

    @NotNull(message = "{cannot.be.null.key}")
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @NotNull(message = "{cannot.be.null.key}")
    @Column(name = "created_on", nullable = false)
    private Date createdOn;
}
