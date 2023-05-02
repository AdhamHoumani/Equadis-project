package com.finance.Finance.transaction.services.impl;

import com.finance.Finance.account.models.Account;
import com.finance.Finance.account.repositories.AccountRepository;
import com.finance.Finance.common.ApiMessageDTO;
import com.finance.Finance.common.ApiResponse;
import com.finance.Finance.common.CommonUtils;
import com.finance.Finance.common.DataValidationRes;
import com.finance.Finance.enums.ApiMessageTypeEnum;
import com.finance.Finance.enums.ApiResponseEnum;
import com.finance.Finance.enums.CustomEnums;
import com.finance.Finance.localehelpers.DataValidationHelper;
import com.finance.Finance.transaction.dtos.TransactionDTO;
import com.finance.Finance.transaction.models.Transaction;
import com.finance.Finance.transaction.repositories.TransactionRepository;
import com.finance.Finance.transaction.services.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    public ApiResponse getAccountTransactions(UUID accountId){
        List<Transaction> transactions = transactionRepository.findAllByAccountId(accountId);
        List<TransactionDTO> transactionDTOS = CommonUtils.mapList(transactions,TransactionDTO.class);
        return new ApiResponse(ApiResponseEnum.SUCCESS.getCode(), transactionDTOS);
    }

    public ApiResponse createTransaction(TransactionDTO transactionDTO){
        List<ApiMessageDTO> messageDTOs = new ArrayList<>();
        Account account = accountRepository.findById(transactionDTO.getAccountId()).orElse(null);
        if(account == null){
            messageDTOs.add(new ApiMessageDTO(ApiMessageTypeEnum.ERROR.getCode(), "Account Not Exist!"));
            return new ApiResponse(ApiResponseEnum.FAILED.getCode(), null,messageDTOs);
        }
        DataValidationRes validationRes = DataValidationHelper.validateTransactionData(transactionDTO);
        if(!validationRes.isSuccess()){
            messageDTOs.add(new ApiMessageDTO(ApiMessageTypeEnum.ERROR.getCode(),validationRes.getMessage()));
            return new ApiResponse(ApiResponseEnum.FAILED.getCode(),messageDTOs);
        }
        if(CustomEnums.TransactionStatus.SUCCESS.equals(CustomEnums.TransactionStatus.valueOf(transactionDTO.getTransactionStatus()))){
            if(CustomEnums.TransactionType.DEPOSIT.equals(CustomEnums.TransactionType.valueOf(transactionDTO.getTransactionType()))){
                account.setBalance(account.getBalance().add(transactionDTO.getAmount()));
            }else{
                account.setBalance(account.getBalance().subtract(transactionDTO.getAmount()));
            }
            accountRepository.save(account);
        }
        Transaction transaction = new Transaction();
        BeanUtils.copyProperties(transactionDTO, transaction, CommonUtils.getNullPropertyNames(transactionDTO));
        modelMapper.map(transactionDTO, transaction);
        transaction.setAccount(account);
        transaction = transactionRepository.save(transaction);
        transactionDTO.setId(transaction.getId());
        messageDTOs.add(new ApiMessageDTO(ApiMessageTypeEnum.SUCCESS.getCode(), "Added Successfully!"));
        return new ApiResponse(ApiResponseEnum.SUCCESS.getCode(),transactionDTO,messageDTOs);
    }
}
