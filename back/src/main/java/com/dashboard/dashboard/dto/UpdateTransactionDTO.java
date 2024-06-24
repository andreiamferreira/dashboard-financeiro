package com.dashboard.dashboard.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public class UpdateTransactionDTO {

    private Integer id;

    @NotBlank
    private Integer bankAccId;

    private LocalDate transactionDate;

    private BigDecimal amount;

    public UpdateTransactionDTO(@NotBlank Integer bankAccId, LocalDate transactionDate, BigDecimal amount) {
        this.bankAccId = bankAccId;
        this.transactionDate = transactionDate;
        this.amount = amount;
    }

    public UpdateTransactionDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBankAccId() {
        return bankAccId;
    }

    public void setBankAccId(Integer bankAccId) {
        this.bankAccId = bankAccId;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
