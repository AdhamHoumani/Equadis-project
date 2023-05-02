package com.core.Core.customer.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class DeleteCustomerRequestDTO {
    private UUID customerId;
    private boolean withCheckingAccounts;
}
