package com.finance.Finance.common;

import com.finance.Finance.enums.ApiMessageTypeEnum;
import com.finance.Finance.enums.ApiResponseEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApiResponse {
    private int status;

    private Object data;

    private List<ApiMessageDTO> messages;

    private String developerMessage;

    public ApiResponse() {
    }
    public ApiResponse(int status, Object data) {
        this.status = status;
        messages = new ArrayList<>();
        this.data = data;
    }
    public ApiResponse(int status, Object data, List<ApiMessageDTO> messages) {
        this.status = status;
        this.data = data;
        this.messages = messages;
    }
    public ApiResponse(int status, Object data, String developerMessage) {
        this.status = status;
        if(status == ApiResponseEnum.EXCEPTION.getCode()){
            this.messages = new ArrayList<>();
            this.messages.add(new ApiMessageDTO(ApiMessageTypeEnum.ERROR.getCode(),"Error Occurred"));
        }
        this.data = data;
        this.developerMessage = developerMessage;
    }
}
