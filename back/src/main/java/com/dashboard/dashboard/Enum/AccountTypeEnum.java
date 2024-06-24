package com.dashboard.dashboard.Enum;

import com.dashboard.dashboard.exceptions.InvalidPathVariableException;

public enum AccountTypeEnum {

    CONTA_CORRENTE,
    POUPANCA,
    SALARIO;

    public static AccountTypeEnum fromString(String typeString) {
        for (AccountTypeEnum type : AccountTypeEnum.values()) {
            if (type.name().equalsIgnoreCase(typeString)) {
                return type;
            }
        }
        throw new InvalidPathVariableException("Invalid account type: " + typeString);
    }
}
