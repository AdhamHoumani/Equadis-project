package com.finance.Finance.account.models;

import com.finance.Finance.enums.CustomEnums;
import com.finance.Finance.transaction.models.Transaction;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Type(type = "pg-uuid")
    private UUID id;

    @NotNull(message = "{cannot.be.null.key}")
    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @NotNull(message = "{cannot.be.null.key}")
    @Column(name = "account_status", columnDefinition = "TEXT", nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomEnums.AccountStatus accountStatus;

    @NotNull(message = "{cannot.be.null.key}")
    @Column(name = "account_type", columnDefinition = "TEXT", nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomEnums.AccountType accountType;

    @NotNull(message = "{cannot.be.null.key}")
    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @NotNull(message = "{cannot.be.null.key}")
    @Column(name = "initial_balance", nullable = false)
    private BigDecimal initialBalance;

    @NotBlank(message = "{should.not.be.empty.key}")
    @Column(unique = true, name = "account_number", nullable = false)
    private String accountNumber;

    @NotBlank(message = "{should.not.be.empty.key}")
    @Column(name = "currency", nullable = false)
    private String currency;
}
