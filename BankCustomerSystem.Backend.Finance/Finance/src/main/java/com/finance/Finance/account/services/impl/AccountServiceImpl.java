package com.finance.Finance.account.services.impl;

import com.finance.Finance.account.dtos.AccountDTO;
import com.finance.Finance.account.models.Account;
import com.finance.Finance.account.repositories.AccountRepository;
import com.finance.Finance.account.services.AccountService;
import com.finance.Finance.common.ApiMessageDTO;
import com.finance.Finance.common.ApiResponse;
import com.finance.Finance.common.CommonUtils;
import com.finance.Finance.common.DataValidationRes;
import com.finance.Finance.enums.ApiMessageTypeEnum;
import com.finance.Finance.enums.ApiResponseEnum;
import com.finance.Finance.enums.CustomEnums;
import com.finance.Finance.feign.CoreApiClient;
import com.finance.Finance.feign.CustomerDTO;
import com.finance.Finance.localehelpers.DataValidationHelper;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CoreApiClient coreApiClient;


    public ApiResponse saveNew(AccountDTO accountDTO) {
        List<ApiMessageDTO> messageDTOList = new ArrayList<ApiMessageDTO>();
        CustomerDTO customer = getCustomerById(accountDTO.getCustomerId());
        if(customer == null){
            messageDTOList.add(new ApiMessageDTO(ApiMessageTypeEnum.ERROR.getCode(),"Customer not found"));
            return new ApiResponse(ApiResponseEnum.FAILED.getCode(),messageDTOList);
        }
        DataValidationRes validationRes = DataValidationHelper.validateAccountStatusData(accountDTO);
        if(!validationRes.isSuccess()){
            messageDTOList.add(new ApiMessageDTO(ApiMessageTypeEnum.ERROR.getCode(),validationRes.getMessage()));
            return new ApiResponse(ApiResponseEnum.FAILED.getCode(),messageDTOList);
        }
        Account account = new Account();
        BeanUtils.copyProperties(accountDTO, account, CommonUtils.getNullPropertyNames(accountDTO));
        modelMapper.map(accountDTO, account);
        account.setCustomerId(customer.getId());
        account = accountRepository.save(account);
        accountDTO.setId(account.getId());
        messageDTOList.add(new ApiMessageDTO(ApiMessageTypeEnum.SUCCESS.getCode(),"Added Successfully!"));
        return new ApiResponse(ApiResponseEnum.SUCCESS.getCode(),accountDTO,messageDTOList);
    }

    public ApiResponse getAccountsByCustomerId(UUID customerId){
        List<ApiMessageDTO> messageDTOList = new ArrayList<ApiMessageDTO>();
        CustomerDTO customer = getCustomerById(customerId);
        if(customer == null){
            messageDTOList.add(new ApiMessageDTO(ApiMessageTypeEnum.ERROR.getCode(),"Customer not found"));
            return new ApiResponse(ApiResponseEnum.FAILED.getCode(),messageDTOList);
        }
        List<Account> accounts = accountRepository.findALlByCustomerId(customerId);
        List<AccountDTO> accountDTOs = CommonUtils.mapList(accounts, AccountDTO.class);
        return new ApiResponse(ApiResponseEnum.SUCCESS.getCode(), accountDTOs);
    }

    public ApiResponse updateAccount(AccountDTO accountDTO) {
        List<ApiMessageDTO> messageDTOList = new ArrayList<ApiMessageDTO>();
        Account accountDb = accountRepository.findById(accountDTO.getId()).orElse(null);
        if(accountDb == null){
            messageDTOList.add(new ApiMessageDTO(ApiMessageTypeEnum.ERROR.getCode(),"Entity Not Found"));
            return new ApiResponse(ApiResponseEnum.FAILED.getCode(), null,messageDTOList);
        }
        BeanUtils.copyProperties(accountDTO,accountDb,CommonUtils.getNullPropertyNames(accountDTO));
        modelMapper.map(accountDTO,accountDb);
        accountRepository.save(accountDb);
        messageDTOList.add(new ApiMessageDTO(ApiMessageTypeEnum.SUCCESS.getCode(),"Updated Successfully!"));
        return new ApiResponse(ApiResponseEnum.SUCCESS.getCode(), accountDTO, messageDTOList);
    }

    public ApiResponse deleteAccount(UUID accountId){
        accountRepository.deleteById(accountId);
        List<ApiMessageDTO> messageDTOList = new ArrayList<ApiMessageDTO>();
        messageDTOList.add(new ApiMessageDTO(ApiMessageTypeEnum.SUCCESS.getCode(),"Deleted Successfully!"));
        return new ApiResponse(ApiResponseEnum.SUCCESS.getCode(), true,messageDTOList);
    }

    private CustomerDTO getCustomerById(UUID customerId) {
        ApiResponse getCustomerResponse = coreApiClient.getCustomerById(customerId);
        Object data = getCustomerResponse.getData();
        if(getCustomerResponse.getStatus() == ApiResponseEnum.SUCCESS.getCode() && data != null){
            CustomerDTO customer = new CustomerDTO();
            if(data instanceof CustomerDTO){
                customer = (CustomerDTO) data;
            }else if(data instanceof LinkedHashMap){
                LinkedHashMap map = (LinkedHashMap) data;
                customer.setId((UUID.fromString(map.get("id").toString())));
                customer.setFirstName((map.get("firstName").toString()));
                customer.setLastName((map.get("lastName").toString()));
                customer.setEmail((map.get("email").toString()));
                customer.setAddress((map.get("address").toString()));
                customer.setPhoneNumber((map.get("phoneNumber").toString()));
            }
            return  customer;
        }
        return null;
    }

    public ApiResponse checkCustomerActiveAccounts(UUID customerId){
        List<Account> accounts = accountRepository.findALlByCustomerId(customerId).stream().filter(a-> a.getAccountStatus() == CustomEnums.AccountStatus.ACTIVE).collect(Collectors.toList());
        if(accounts.size() > 0){
            return new ApiResponse(ApiResponseEnum.SUCCESS.getCode(), true);
        }
        return new ApiResponse(ApiResponseEnum.SUCCESS.getCode(), false);
    }

    public ApiResponse deleteCustomerInfo(UUID customerId){
        List<Account> accounts = accountRepository.findALlByCustomerId(customerId);
        accountRepository.deleteAll(accounts);
        return new ApiResponse(ApiResponseEnum.SUCCESS.getCode(), true);
    }
}
