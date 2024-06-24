package com.dashboard.dashboard.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateGoalDTO {

    private String description;

    private BigDecimal amount;

    private LocalDate limitDate;

    public CreateGoalDTO() {
    }

    public CreateGoalDTO(String description, BigDecimal amount, LocalDate limitDate) {
        this.description = description;
        this.amount = amount;
        this.limitDate = limitDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(LocalDate limitDate) {
        this.limitDate = limitDate;
    }

}
