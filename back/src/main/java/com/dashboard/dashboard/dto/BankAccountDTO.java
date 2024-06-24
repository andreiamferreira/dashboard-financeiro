package com.dashboard.dashboard.dto;

import java.math.BigDecimal;

import com.dashboard.dashboard.Enum.AccountTypeEnum;
import com.dashboard.dashboard.Enum.InstitutionEnum;

import lombok.Data;

@Data
public class BankAccountDTO {

    private Integer id;

    private Integer userId;

    private AccountTypeEnum accountType;

    private InstitutionEnum institution;

    private BigDecimal balance;

    public BankAccountDTO() {
    }

    public BankAccountDTO(Integer id, Integer userId, AccountTypeEnum accountType, InstitutionEnum institution,
            BigDecimal balance) {
        this.id = id;
        this.userId = userId;
        this.accountType = accountType;
        this.institution = institution;
        this.balance = balance;
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

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public InstitutionEnum getInstitution() {
        return institution;
    }

    public void setInstitution(InstitutionEnum institution) {
        this.institution = institution;
    }

    public AccountTypeEnum getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountTypeEnum accountType) {
        this.accountType = accountType;
    }

}
