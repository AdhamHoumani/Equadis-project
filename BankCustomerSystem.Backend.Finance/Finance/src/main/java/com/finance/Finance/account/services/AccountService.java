package com.finance.Finance.account.services;

import com.finance.Finance.account.dtos.AccountDTO;
import com.finance.Finance.common.ApiResponse;

import java.util.UUID;

public interface AccountService {
    ApiResponse saveNew(AccountDTO accountDTO);
    ApiResponse getAccountsByCustomerId(UUID customerId);
    ApiResponse deleteAccount(UUID accountId);
    ApiResponse updateAccount(AccountDTO accountDTO);
    ApiResponse checkCustomerActiveAccounts(UUID customerId);
    ApiResponse deleteCustomerInfo(UUID customerId);
}
