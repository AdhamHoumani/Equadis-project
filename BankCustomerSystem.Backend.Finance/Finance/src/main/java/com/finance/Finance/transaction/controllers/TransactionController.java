package com.finance.Finance.transaction.controllers;

import com.finance.Finance.common.ApiResponse;
import com.finance.Finance.enums.ApiResponseEnum;
import com.finance.Finance.transaction.dtos.TransactionDTO;
import com.finance.Finance.transaction.models.Transaction;
import com.finance.Finance.transaction.services.TransactionService;
import com.finance.Finance.transaction.services.impl.TransactionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transaction")
@Slf4j

public class TransactionController {
    @Autowired
    private TransactionServiceImpl transactionService;

    @PostMapping()
    ApiResponse createTransaction(@RequestBody TransactionDTO transactionDTO){
        try
        {
            log.info("Create transaction call started with account id : " + transactionDTO.toString());
            ApiResponse response = transactionService.createTransaction(transactionDTO);
            log.info("Create transaction call started with response : " + response);
            return response;
        }
        catch (Exception ex)
        {
            log.error("Create transaction call failed with exception : " + ex.getMessage());
            return new ApiResponse(ApiResponseEnum.EXCEPTION.getCode(), null,ex.getMessage());
        }
    }

    @GetMapping("{accountId}")
    ApiResponse getAccountTransactions(@PathVariable UUID accountId){
        try
        {
            log.info("Get transactions by accountId call started with account id : " + accountId.toString());
            ApiResponse response = transactionService.getAccountTransactions(accountId);
            log.info("Get transactions by accountId call started with response : " + response);
            return response;
        }
        catch (Exception ex)
        {
            log.error("Get transactions by accountId call failed with exception : " + ex.getMessage());
            return new ApiResponse(ApiResponseEnum.EXCEPTION.getCode(), null,ex.getMessage());
        }
    }

}
