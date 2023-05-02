package com.finance.Finance.localehelpers;

import com.finance.Finance.common.CommonUtils;
import com.finance.Finance.common.DataValidationRes;
import com.finance.Finance.account.dtos.AccountDTO;
import com.finance.Finance.transaction.dtos.TransactionDTO;


public final class DataValidationHelper {

    public static DataValidationRes validateAccountStatusData(AccountDTO accountDTO){
        if (CommonUtils.isEmpty(accountDTO.getCurrency())) {
            return new DataValidationRes(false,"Currency Required");
        }
        return new DataValidationRes(true);
    }
    public static DataValidationRes validateTransactionData(TransactionDTO transactionDTO){
        return new DataValidationRes(true);
    }
}
