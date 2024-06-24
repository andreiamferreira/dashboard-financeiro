package com.dashboard.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dashboard.dashboard.model.BankAccount;
import com.dashboard.dashboard.model.User;

import java.util.List;
import java.util.Optional;

import com.dashboard.dashboard.Enum.InstitutionEnum;
import com.dashboard.dashboard.Enum.AccountTypeEnum;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {

    List<BankAccount> findByUser(User user);

    List<BankAccount> findAllByUserId(Integer id);

    List<BankAccount> findByInstitution(InstitutionEnum institution);

    List<BankAccount> findByAccountType(AccountTypeEnum accountType);

    Optional<BankAccount> findByIdAndUser(Integer id, User user);

    List<BankAccount> findByAccountTypeAndUser(AccountTypeEnum type, User user);

    List<BankAccount> findByInstitutionAndUser(InstitutionEnum institution, User user);

}
