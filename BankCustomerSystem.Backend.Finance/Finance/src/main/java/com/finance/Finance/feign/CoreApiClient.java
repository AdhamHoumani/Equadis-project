package com.finance.Finance.feign;

import com.finance.Finance.common.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;


@FeignClient(value = "${app.feign.config.name.profile}", url = "${app.feign.config.url.profile}")
public interface CoreApiClient {
    @GetMapping("/customer/{id}")
    ApiResponse getCustomerById(@PathVariable UUID id);

}