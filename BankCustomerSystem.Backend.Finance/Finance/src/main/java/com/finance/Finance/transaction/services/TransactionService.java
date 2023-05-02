package com.finance.Finance.transaction.services;

import com.finance.Finance.common.ApiResponse;
import com.finance.Finance.transaction.dtos.TransactionDTO;

import java.util.UUID;

public interface TransactionService {
    ApiResponse getAccountTransactions(UUID accountId);
    ApiResponse createTransaction(TransactionDTO transactionDTO);
}
