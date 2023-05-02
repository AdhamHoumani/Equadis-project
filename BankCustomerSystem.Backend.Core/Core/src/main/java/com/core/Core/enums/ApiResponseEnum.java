package com.core.Core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApiResponseEnum {
    SUCCESS(1,"Success"),
    FAILED(0,"Failed"),
    EXCEPTION(-1,"Exception");

    private final int code;
    private final String name;
}
