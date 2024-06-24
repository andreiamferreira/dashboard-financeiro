package com.dashboard.dashboard.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dashboard.dashboard.model.FinancialGoal;
import com.dashboard.dashboard.model.User;

import java.time.LocalDate;
import java.util.Date;

@Repository
public interface FinancialGoalRepository extends JpaRepository<FinancialGoal, Integer> {

    List<FinancialGoal> findAllByUser(User user);

    Optional<FinancialGoal> findByUserAndId(User user, Integer id);

    Optional<FinancialGoal> findByUserAndAmount(User user, Double amount);

    List<FinancialGoal> findByAmount(Double amount);

    List<FinancialGoal> findByLimitDate(Date limitDate);

    List<FinancialGoal> findByUserAndLimitDateGreaterThanEqualOrderByLimitDateDesc(User user, LocalDate date);

}
