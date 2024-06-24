package com.dashboard.dashboard.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.dashboard.dashboard.model.FinancialGoal;

public class FinancialGoalDTO {
    private Integer id;

    private Integer userId;

    private String description;

    private BigDecimal amount;

    private LocalDate limitDate;

    private Boolean isCompleted;

    private Boolean isOverdue;

    public FinancialGoalDTO() {
    }

    public FinancialGoalDTO(Integer id, Integer userId, String description, BigDecimal amount, LocalDate limitDate,
            Boolean isCompleted, Boolean isOverdue) {
        this.id = id;
        this.userId = userId;
        this.description = description;
        this.amount = amount;
        this.limitDate = limitDate;
        this.isCompleted = isCompleted;
        this.isOverdue = isOverdue;
    }

    public FinancialGoalDTO(FinancialGoal financialGoal) {
        userId = financialGoal.getUser().getId();
        description = financialGoal.getDescription();
        amount = financialGoal.getAmount();
        limitDate = financialGoal.getLimitDate();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUser(Integer userId) {
        this.userId = userId;
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

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Boolean getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(Boolean isOverdue) {
        this.isOverdue = isOverdue;
    }

}
