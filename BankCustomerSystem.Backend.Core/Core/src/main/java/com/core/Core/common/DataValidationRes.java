package com.core.Core.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataValidationRes {
    private boolean isSuccess;
    private String Message;

    public DataValidationRes(boolean isSuccess, String Message){
        this.isSuccess = isSuccess;
        this.Message = Message;
    }

    public DataValidationRes(boolean isSuccess){
        this.isSuccess = isSuccess;
        this.Message = "";
    }
}
