package com.dashboard.dashboard.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.dashboard.dashboard.Enum.TransactionCategories;
import com.dashboard.dashboard.Enum.TransactionTypeEnum;
import com.dashboard.dashboard.model.Transaction;

import jakarta.validation.constraints.NotBlank;

public class TransactionDTO {

    private Integer id;

    @NotBlank
    private Integer bankAccId;

    @NotBlank
    private TransactionTypeEnum transactionType;

    @NotBlank
    private TransactionCategories category;

    private LocalDate transactionDate;

    private BigDecimal amount;

    private Boolean isOut;

    public TransactionDTO() {
    }

    public TransactionDTO(Integer id, Integer bankAccId, TransactionTypeEnum transactionType,
            TransactionCategories category, BigDecimal amount, Boolean isOut, LocalDate transactionDate) {
        this.id = id;
        this.bankAccId = bankAccId;
        this.transactionType = transactionType;
        this.category = category;
        this.amount = amount;
        this.isOut = isOut;
        this.transactionDate = transactionDate;
    }

    public TransactionDTO(Transaction transaction) {
        id = transaction.getId();
        bankAccId = transaction.getBankAccount().getId();
        transactionType = transaction.getTransactionType();
        category = transaction.getCategory();
        amount = transaction.getAmount();
        isOut = transaction.getIsOut();
        transactionDate = transaction.getTransactionDate();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean getIsOut() {
        return isOut;
    }

    public void setIsOut(Boolean isOut) {
        this.isOut = isOut;
    }

    public Integer getBankAccId() {
        return bankAccId;
    }

    public void setBankAccId(Integer bankAccId) {
        this.bankAccId = bankAccId;
    }

    public TransactionTypeEnum getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypeEnum transactionType) {
        this.transactionType = transactionType;
    }

    public TransactionCategories getCategory() {
        return category;
    }

    public void setCategory(TransactionCategories category) {
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

}
