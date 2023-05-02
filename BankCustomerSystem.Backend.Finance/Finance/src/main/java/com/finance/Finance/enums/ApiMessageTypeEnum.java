package com.finance.Finance.enums;

public enum ApiMessageTypeEnum {
    WARNING(0,"Warning"),
    INFORMATION(2,"Information"),
    SUCCESS(1,"Success"),
    ERROR(-1,"Failed"),
    EXCEPTION(-2,"Exception");

    private final int code;
    private final String name;

    ApiMessageTypeEnum(int code,String name){
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
