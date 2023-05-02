package com.finance.Finance.account.controllers;

import com.finance.Finance.account.dtos.AccountDTO;
import com.finance.Finance.account.services.AccountService;
import com.finance.Finance.common.ApiResponse;
import com.finance.Finance.enums.ApiResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("{customerId}")
    ApiResponse getALlAccountByCustomerId(@PathVariable UUID customerId) {
        try{
            log.info("Get all accounts by customer id call started with customer id : " + customerId.toString());
            ApiResponse response = accountService.getAccountsByCustomerId(customerId);
            log.info("Get all accounts by customer id call finished with response : " + response);
            return response;
        }catch (Exception ex){
            log.error("Get all accounts call failed with exception : " + ex.getMessage());
            return new ApiResponse(ApiResponseEnum.EXCEPTION.getCode(), null,ex.getMessage());
        }
    }
    @PostMapping()
    ApiResponse saveNew(@RequestBody AccountDTO accountDTO) {
        try{
            log.info("Saving account call started with request : " + accountDTO.toString());
            ApiResponse response = accountService.saveNew(accountDTO);
            log.info("Saving account call finished with response : " + response);
            return response;
        }catch (Exception ex){
            log.error("Saving account call failed with exception : " + ex.getMessage());
            return new ApiResponse(ApiResponseEnum.EXCEPTION.getCode(), null,ex.getMessage());
        }
    }
    @PutMapping()
    public ApiResponse updateAccount(@RequestBody AccountDTO accountDTO) {
        try
        {
            log.info("Update Account call started with request : " + accountDTO);
            ApiResponse response =  accountService.updateAccount(accountDTO);
            log.info("Update Account call finished with response : " + response);
            return response;
        }
        catch (Exception ex)
        {
            log.error("Update Account call failed with exception : " + ex.getMessage());
            return new ApiResponse(ApiResponseEnum.EXCEPTION.getCode(), null,ex.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ApiResponse deleteAccount(@PathVariable UUID id) {
        try
        {
            log.info("Delete Account call started with request : " + id);
            ApiResponse response =  accountService.deleteAccount(id);
            log.info("Delete Account call finished with response : " + response);
            return response;
        }
        catch (Exception ex)
        {
            log.error("Delete Account call failed with exception : " + ex.getMessage());
            return new ApiResponse(ApiResponseEnum.EXCEPTION.getCode(), null,ex.getMessage());
        }
    }

    @GetMapping("/checkCustomerActiveAccounts/{customerId}")
    public ApiResponse checkCustomerActiveAccounts(@PathVariable UUID customerId){
        try
        {
            log.info("checkCustomerActiveAccounts call started with request : " + customerId);
            ApiResponse response =  accountService.checkCustomerActiveAccounts(customerId);
            log.info("checkCustomerActiveAccounts call finished with response : " + response);
            return response;
        }
        catch (Exception ex)
        {
            log.error("checkCustomerActiveAccounts call failed with exception : " + ex.getMessage());
            return new ApiResponse(ApiResponseEnum.EXCEPTION.getCode(), null,ex.getMessage());
        }
    }

    @DeleteMapping("/deleteCustomerInfo/{customerId}")
    public ApiResponse deleteCustomerInfo(@PathVariable UUID customerId){
        try
        {
            log.info("deleteCustomerInfo call started with request : " + customerId);
            ApiResponse response =  accountService.deleteCustomerInfo(customerId);
            log.info("deleteCustomerInfo call finished with response : " + response);
            return response;
        }
        catch (Exception ex)
        {
            log.error("checkCustomerActiveAccounts call failed with exception : " + ex.getMessage());
            return new ApiResponse(ApiResponseEnum.EXCEPTION.getCode(), null,ex.getMessage());
        }
    }

}
