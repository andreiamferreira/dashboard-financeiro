package com.dashboard.dashboard.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dashboard.dashboard.Enum.TransactionCategories;
import com.dashboard.dashboard.model.BankAccount;
import com.dashboard.dashboard.model.Transaction;
import com.dashboard.dashboard.Enum.TransactionTypeEnum;
import java.time.LocalDate;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findAllByBankAccount(BankAccount bankAccount);

    Optional<Transaction> findByIdAndBankAccount(Integer id, BankAccount bankAccount);

    List<Transaction> findByCategoryAndBankAccount(TransactionCategories category, BankAccount account);

    List<Transaction> findByTransactionTypeAndBankAccount(TransactionTypeEnum transactionType, BankAccount account);

    List<Transaction> findByBankAccountAndTransactionDateLessThanEqualOrderByTransactionDateDesc(BankAccount account,
            LocalDate date);

    List<Transaction> findByBankAccountAndIsOutTrue(BankAccount account);

    List<Transaction> findByBankAccountAndIsOutFalse(BankAccount account);

}
