package com.finance.Finance.enums;

public enum ApiResponseEnum {
    SUCCESS(1,"Success"),
    FAILED(0,"Failed"),
    EXCEPTION(-1,"Exception");

    private final int code;
    private final String name;

    ApiResponseEnum(int code,String name){
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
