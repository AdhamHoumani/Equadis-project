package com.core.Core.feign;

import com.core.Core.common.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(value = "${app.feign.config.name.profile}", url = "${app.feign.config.url.profile}")
public interface FinanceApiClient {
    @GetMapping("/account/checkCustomerActiveAccounts/{customerId}")
    ApiResponse checkCustomerActiveAccounts(@PathVariable UUID customerId);

    @DeleteMapping("/account/deleteCustomerInfo/{customerId}")
    ApiResponse deleteCustomerInfo(@PathVariable UUID customerId);
}
