package com.dashboard.dashboard.Enum;

public enum TransactionCategories {
    RESTAURANTE, // out
    MERCADO, // out
    LAZER, // out
    FARMACIA, // out
    SALARIO, // in
    ACADEMIA, // out
    ASSINATURA, // out
    EDUCACAO, // out
    MEDICO, // out
    EMPRESTIMO, // in
    FATURA, // out
    OUTROS; // ?

    public static TransactionCategories fromString(String categoryString) {
        for (TransactionCategories category : TransactionCategories.values()) {
            if (category.name().equalsIgnoreCase(categoryString)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid category: " + categoryString);
    }

}
