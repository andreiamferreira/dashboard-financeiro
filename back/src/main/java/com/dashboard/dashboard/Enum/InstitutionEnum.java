package com.dashboard.dashboard.Enum;

import com.dashboard.dashboard.exceptions.InvalidPathVariableException;

public enum InstitutionEnum {

    BANCO_DO_BRASIL,
    ITAU,
    BRADESCO,
    SANTANDER,
    NUBANK,
    INTER,
    CAIXA_ECONOMICA,
    C6,
    BANRISUL,
    PAGBANK;

    public static InstitutionEnum fromString(String institutionString) {
        for (InstitutionEnum institution : InstitutionEnum.values()) {
            if (institution.name().equalsIgnoreCase(institutionString)) {
                return institution;
            }
        }
        throw new InvalidPathVariableException("Invalid account institution: " + institutionString);
    }
}
