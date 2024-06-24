package com.dashboard.dashboard.Enum;

import com.dashboard.dashboard.exceptions.InvalidPathVariableException;

public enum TransactionTypeEnum {
    DEBITO,
    CREDITO,
    PIX;

    public static TransactionTypeEnum fromString(String typeString) {
        for (TransactionTypeEnum type : TransactionTypeEnum.values()) {
            if (type.name().equalsIgnoreCase(typeString)) {
                return type;
            }
        }
        throw new InvalidPathVariableException("Invalid transaction type: " + typeString);
    }
}
