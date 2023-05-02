package com.core.Core.customer.controller;

import com.core.Core.common.ApiResponse;
import com.core.Core.customer.dtos.CustomerDTO;
import com.core.Core.customer.dtos.DeleteCustomerRequestDTO;
import com.core.Core.customer.services.CustomerService;
import com.core.Core.enums.ApiResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping()
    public ApiResponse getAllCustomers() {
        try
        {
            log.info("Get All Customers call started");
            ApiResponse response =  customerService.getAllCustomers();
            log.info("Get All Customers call finished with response : " + response);
            return response;
        }
        catch (Exception ex)
        {
            log.error("Get All Customers call failed with exception : " + ex.getMessage());
            return new ApiResponse(ApiResponseEnum.EXCEPTION.getCode(), null,ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ApiResponse getCustomerById(@PathVariable UUID id) {
        try
        {
            log.info("Get Customer By Id call started with id : " + id);
            ApiResponse response =  customerService.getCustomerById(id);
            log.info("Get Customer By Id call finished with response : " + response);
            return response;
        }
        catch (Exception ex)
        {
            log.error("Get Customer By Id call failed with exception : " + ex.getMessage());
            return new ApiResponse(ApiResponseEnum.EXCEPTION.getCode(), null,ex.getMessage());
        }
    }

    @PostMapping()
    public ApiResponse addCustomer(@RequestBody CustomerDTO customer) {
        try
        {
            log.info("Add Customer call started with request : " + customer);
            ApiResponse response =  customerService.addCustomer(customer);
            log.info("Add Customer call finished with response : " + response);
            return response;
        }
        catch (Exception ex)
        {
            log.error("Add Customer call failed with exception : " + ex.getMessage());
            return new ApiResponse(ApiResponseEnum.EXCEPTION.getCode(), null,ex.getMessage());
        }
    }

    @PutMapping()
    public ApiResponse updateCustomer(@RequestBody CustomerDTO customer) {
        try
        {
            log.info("Update Customer call started with request : " + customer);
            ApiResponse response =  customerService.updateCustomer(customer);
            log.info("Update Customer call finished with response : " + response);
            return response;
        }
        catch (Exception ex)
        {
            log.error("Update Customer call failed with exception : " + ex.getMessage());
            return new ApiResponse(ApiResponseEnum.EXCEPTION.getCode(), null,ex.getMessage());
        }
    }
    @PostMapping("delete")
    public ApiResponse deleteCustomer(@RequestBody DeleteCustomerRequestDTO request) {
        try
        {
            log.info("Delete Customer call started with request : " + request);
            ApiResponse response =  customerService.deleteCustomer(request);
            log.info("Delete Customer call finished with response : " + response);
            return response;
        }
        catch (Exception ex)
        {
            log.error("Delete Customer call failed with exception : " + ex.getMessage());
            return new ApiResponse(ApiResponseEnum.EXCEPTION.getCode(), null,ex.getMessage());
        }
    }
}
