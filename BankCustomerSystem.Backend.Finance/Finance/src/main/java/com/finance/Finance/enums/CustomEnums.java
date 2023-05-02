package com.finance.Finance.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CustomEnums {

    @Getter
    @AllArgsConstructor
    public enum AccountStatus {
        ACTIVE("Active"),
        BLOCKED("Blocked"),
        ON_HOLD("On Hold");
        private final String name;
    }

    @Getter
    @AllArgsConstructor
    public enum AccountType {
        EXTERNAL("External"),
        INTERNAL("Internal");
        private final String name;
    }

    @Getter
    @AllArgsConstructor
    public enum TransactionStatus{
        FAILED("Failed"),
        SUCCESS("Success"),
        PENDING("Pending");
        private final String name;
    }

    @Getter
    @AllArgsConstructor
    public enum TransactionType{
        DEPOSIT("Deposit"),
        WITHDRAW("Success");
        private final String name;
    }
}
