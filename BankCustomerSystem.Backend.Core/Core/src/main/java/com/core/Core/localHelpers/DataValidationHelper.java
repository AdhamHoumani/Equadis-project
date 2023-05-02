package com.core.Core.localHelpers;

import com.core.Core.common.CommonUtils;
import com.core.Core.common.DataValidationRes;
import com.core.Core.customer.dtos.CustomerDTO;

public final class DataValidationHelper {

    public static DataValidationRes validateCustomerData(CustomerDTO customerDTO){
        if (CommonUtils.isEmpty(customerDTO.getFirstName())) {
            return new DataValidationRes(false,"First Name Required");
        }else if (CommonUtils.isEmpty(customerDTO.getLastName())) {
            return new DataValidationRes(false,"Last Name Required");
        }else if (CommonUtils.isEmpty(customerDTO.getEmail())) {
            return new DataValidationRes(false,"Email Required");
        }else if (CommonUtils.isEmpty(customerDTO.getPhoneNumber())) {
            return new DataValidationRes(false,"Phone Number");
        }
        return new DataValidationRes(true);
    }
}
