package com.core.Core.customer.services.impl;

import com.core.Core.common.ApiMessageDTO;
import com.core.Core.common.ApiResponse;
import com.core.Core.common.CommonUtils;
import com.core.Core.common.DataValidationRes;
import com.core.Core.customer.dtos.CustomerDTO;
import com.core.Core.customer.dtos.DeleteCustomerRequestDTO;
import com.core.Core.customer.models.Customer;
import com.core.Core.customer.repositories.CustomerRepository;
import com.core.Core.customer.services.CustomerService;
import com.core.Core.enums.ApiMessageTypeEnum;
import com.core.Core.enums.ApiResponseEnum;
import com.core.Core.feign.FinanceApiClient;
import com.core.Core.localHelpers.DataValidationHelper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private FinanceApiClient financeAppClient;

    @Override
    public ApiResponse getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOS = CommonUtils.mapList(customers,CustomerDTO.class);
        return new ApiResponse(ApiResponseEnum.SUCCESS.getCode(),customerDTOS);
    }

    @Override
    public ApiResponse getCustomerById(UUID id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO,CommonUtils.getNullPropertyNames(customerDTO));
        modelMapper.map(customer,customerDTO);
        return new ApiResponse(ApiResponseEnum.SUCCESS.getCode(),customer);
    }

    @Override
    public ApiResponse addCustomer(CustomerDTO customerDTO) {
        List<ApiMessageDTO> messageDTOList = new ArrayList<ApiMessageDTO>();
        log.info("Saving customer " + customerDTO.toString());
        List<ApiMessageDTO> checkingUniqueCredentials = checkCredentialsIfUnique(customerDTO.getEmail(),customerDTO.getPhoneNumber());
        if(checkingUniqueCredentials.size() > 0){
            return new ApiResponse(ApiResponseEnum.FAILED.getCode(), null,checkingUniqueCredentials);
        }
        DataValidationRes validationRes = DataValidationHelper.validateCustomerData(customerDTO);
        if(!validationRes.isSuccess()){
            messageDTOList.add(new ApiMessageDTO(ApiMessageTypeEnum.ERROR.getCode(),validationRes.getMessage()));
            return new ApiResponse(ApiResponseEnum.FAILED.getCode(), null,messageDTOList);
        }
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO,customer,CommonUtils.getNullPropertyNames(customerDTO));
        modelMapper.map(customerDTO,customer);

        Customer savedCustomer = customerRepository.save(customer);
        customerDTO.setId(savedCustomer.getId());
        messageDTOList.add(new ApiMessageDTO(ApiMessageTypeEnum.SUCCESS.getCode(),"Added Successfully!"));
        return new ApiResponse(ApiResponseEnum.SUCCESS.getCode(), customerDTO,messageDTOList);
    }
    @Override
    public ApiResponse updateCustomer(CustomerDTO customerDTO) {
        List<ApiMessageDTO> messageDTOList = new ArrayList<ApiMessageDTO>();
        List<ApiMessageDTO> checkingUniqueCredentials = checkCredentialsIfUnique(customerDTO.getEmail(),customerDTO.getPhoneNumber());
        if(checkingUniqueCredentials.size() > 0){
            return new ApiResponse(ApiResponseEnum.FAILED.getCode(), null,checkingUniqueCredentials);
        }
        Customer customerdb = customerRepository.findById(customerDTO.getId()).orElse(null);
        if(customerdb == null){
            messageDTOList.add(new ApiMessageDTO(ApiMessageTypeEnum.ERROR.getCode(),"Entity Not Found"));
            return new ApiResponse(ApiResponseEnum.FAILED.getCode(), null,messageDTOList);
        }
        BeanUtils.copyProperties(customerDTO,customerdb,CommonUtils.getNullPropertyNames(customerDTO));
        modelMapper.map(customerDTO,customerdb);
        customerRepository.save(customerdb);
        messageDTOList.add(new ApiMessageDTO(ApiMessageTypeEnum.SUCCESS.getCode(),"Updated Successfully!"));
        return new ApiResponse(ApiResponseEnum.SUCCESS.getCode(), customerDTO, messageDTOList);
    }

    @Override
    public ApiResponse deleteCustomer(DeleteCustomerRequestDTO requestDTO) {
        List<ApiMessageDTO> messageDTOList = new ArrayList<ApiMessageDTO>();
        //checking if customer has active accounts
        if(requestDTO.isWithCheckingAccounts()){
            ApiResponse financeResponse = financeAppClient.checkCustomerActiveAccounts(requestDTO.getCustomerId());
            if(!(financeResponse.getStatus() == ApiResponseEnum.SUCCESS.getCode())){
                messageDTOList.add(new ApiMessageDTO(ApiMessageTypeEnum.ERROR.getCode(),"Cannot Delete This Customer!"));
                return new ApiResponse(ApiResponseEnum.FAILED.getCode(), true,messageDTOList);
            }
            if((boolean) financeResponse.getData()){
                messageDTOList.add(new ApiMessageDTO(ApiMessageTypeEnum.WARNING.getCode(),"Customer Has Active Accounts!"));
                return new ApiResponse(ApiResponseEnum.SUCCESS.getCode(), false,messageDTOList);
            }
        }
        // delete customer's finance information
        ApiResponse deleteCustomerFinanceInfoResponse = financeAppClient.deleteCustomerInfo(requestDTO.getCustomerId());
        if(!(deleteCustomerFinanceInfoResponse.getStatus() == ApiResponseEnum.SUCCESS.getCode())){
            messageDTOList.add(new ApiMessageDTO(ApiMessageTypeEnum.ERROR.getCode(),"Cannot Delete This Customer!"));
            return new ApiResponse(ApiResponseEnum.FAILED.getCode(), true,messageDTOList);
        }
        customerRepository.deleteById(requestDTO.getCustomerId());
        messageDTOList.add(new ApiMessageDTO(ApiMessageTypeEnum.SUCCESS.getCode(),"Deleted Successfully!"));
        return new ApiResponse(ApiResponseEnum.SUCCESS.getCode(), true,messageDTOList);
    }

    private List<ApiMessageDTO> checkCredentialsIfUnique(String email, String phoneNumber){
        List<ApiMessageDTO> res = new ArrayList<>();
        Customer customer = customerRepository.findByEmail(email);
        if(customer != null){
            res.add(new ApiMessageDTO(ApiMessageTypeEnum.WARNING.getCode(),"This Email already used!"));
        }
        customer = customerRepository.findByPhoneNumber(phoneNumber);
        if(customer != null){
            res.add(new ApiMessageDTO(ApiMessageTypeEnum.WARNING.getCode(),"This Mobile already used!"));
        }
        return res;
    }
}
