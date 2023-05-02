package com.core.Core.customer.services;

import com.core.Core.common.ApiResponse;
import com.core.Core.customer.dtos.CustomerDTO;
import com.core.Core.customer.dtos.DeleteCustomerRequestDTO;

import java.util.UUID;

public interface CustomerService {
    ApiResponse getAllCustomers();
    ApiResponse getCustomerById(UUID id);
    ApiResponse addCustomer(CustomerDTO customerDTO);
    ApiResponse updateCustomer(CustomerDTO customerDTO);
    ApiResponse deleteCustomer(DeleteCustomerRequestDTO requestDTO);
}
