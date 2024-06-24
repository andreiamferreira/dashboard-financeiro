package com.dashboard.dashboard.dto;

import java.math.BigDecimal;
import java.util.List;

public class SumOfAccountsByInstitutionDTO {

    private List<BankAccountDTO> accounts;
    private BigDecimal totalBalance;
    private Integer numberOfAccounts;

    public SumOfAccountsByInstitutionDTO() {
    }

    public SumOfAccountsByInstitutionDTO(List<BankAccountDTO> accounts, BigDecimal totalBalance,
            Integer numberOfAccounts) {
        this.accounts = accounts;
        this.totalBalance = totalBalance;
        this.numberOfAccounts = numberOfAccounts;
    }

    public List<BankAccountDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<BankAccountDTO> accounts) {
        this.accounts = accounts;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public Integer getNumberOfAccounts() {
        return numberOfAccounts;
    }

    public void setNumberOfAccounts(Integer numberOfAccounts) {
        this.numberOfAccounts = numberOfAccounts;
    }

}
