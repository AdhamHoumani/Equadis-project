package com.core.Core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApiMessageTypeEnum {
    WARNING(0,"Warning"),
    INFORMATION(2,"Information"),
    SUCCESS(1,"Success"),
    ERROR(-1,"Failed"),
    EXCEPTION(-2,"Exception");

    private final int code;
    private final String name;
}
