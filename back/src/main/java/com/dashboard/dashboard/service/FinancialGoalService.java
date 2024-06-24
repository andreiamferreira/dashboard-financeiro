package com.dashboard.dashboard.service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.dashboard.dashboard.dto.CompleteGoalDTO;
import com.dashboard.dashboard.dto.CreateGoalDTO;
import com.dashboard.dashboard.dto.FinancialGoalDTO;
import com.dashboard.dashboard.model.FinancialGoal;
import com.dashboard.dashboard.model.User;
import com.dashboard.dashboard.repository.FinancialGoalRepository;
import com.dashboard.dashboard.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class FinancialGoalService {

    @Autowired
    private FinancialGoalRepository financialGoalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    private FinancialGoalDTO toFinancialGoalDTO(FinancialGoal financialGoal) {
        return new FinancialGoalDTO(financialGoal.getId(), financialGoal.getUser().getId(),
                financialGoal.getDescription(),
                financialGoal.getAmount(),
                financialGoal.getLimitDate(), financialGoal.getIsCompleted(), financialGoal.getIsOverdue());
    }

    private FinancialGoal toGoal(CreateGoalDTO goalDTO) {
        var goal = new FinancialGoal();
        goal.setDescription(goalDTO.getDescription());
        goal.setLimitDate(goalDTO.getLimitDate());
        goal.setAmount(goalDTO.getAmount());

        return goal;
    }

    private User getUser(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "User not found - Id: " + userId));
    }

    Instant currentTime = Instant.now();
    Date today = Date.from(currentTime);

    @Transactional
    public List<FinancialGoalDTO> getAllGoals(Integer userId) {

        User user = getUser(userId);
        List<FinancialGoal> goals = financialGoalRepository.findAllByUser(user);

        return goals.stream().map(this::toFinancialGoalDTO).collect(Collectors.toList());
    }

    @Transactional
    public FinancialGoalDTO getGoalById(Integer userId, Integer goalId) {

        User user = getUser(userId);
        Optional<FinancialGoal> goal = financialGoalRepository.findByUserAndId(user, goalId);

        return modelMapper.map(goal, FinancialGoalDTO.class);
    }

    @Transactional
    public List<FinancialGoalDTO> getCloserLimitDate(Integer userId) {

        User user = getUser(userId);

        LocalDate today = LocalDate.now();

        List<FinancialGoal> closerLimitDates = financialGoalRepository
                .findByUserAndLimitDateGreaterThanEqualOrderByLimitDateDesc(user, today);

        return closerLimitDates.stream().map(this::toFinancialGoalDTO).collect(Collectors.toList());
    }

    @Transactional
    public CreateGoalDTO addGoal(User currentUser, CreateGoalDTO financialGoalDTO) {

        LocalDate currentDate = LocalDate.now();

        if (financialGoalDTO.getLimitDate().isBefore(currentDate)) {
            throw new IllegalArgumentException("Can't add a goal with a past date.");
        }

        FinancialGoal goal = toGoal(financialGoalDTO);

        goal.setIsOverdue(false);
        goal.setIsCompleted(false);

        goal.setUser(currentUser);

        FinancialGoal newGoal = financialGoalRepository.save(goal);

        return new CreateGoalDTO(
                newGoal.getDescription(),
                newGoal.getAmount(),
                newGoal.getLimitDate());

    }

    @Transactional
    public FinancialGoalDTO updateGoal(User currentUser, Integer id, FinancialGoalDTO financialGoalDTO) {

        FinancialGoal goal = financialGoalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Goal not found with ID: " + id));

        LocalDate currentDate = LocalDate.now();

        if (financialGoalDTO.getLimitDate().isBefore(currentDate)) {
            throw new IllegalArgumentException("Can't add a goal with a past date.");
        }

        goal.setDescription(financialGoalDTO.getDescription());
        goal.setAmount(financialGoalDTO.getAmount());
        goal.setLimitDate(financialGoalDTO.getLimitDate());
        goal.setUser(currentUser);
        goal.setUpdatedAt(today);
        goal.updateOverdueStatus(currentDate);
        System.out.println(goal);

        FinancialGoal updatedGoal = financialGoalRepository.save(goal);

        return new FinancialGoalDTO(
                updatedGoal.getId(),
                updatedGoal.getUser().getId(),
                updatedGoal.getDescription(),
                updatedGoal.getAmount(),
                updatedGoal.getLimitDate(),
                updatedGoal.getIsCompleted(),
                updatedGoal.getIsOverdue());
    }

    @Transactional
    public CompleteGoalDTO completeGoal(User currentUser, Integer id, CompleteGoalDTO goalDTO) {

        FinancialGoal goal = financialGoalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Goal not found with ID: " + id));

        goal.setUser(currentUser);

        goal.setUpdatedAt(today);
        goal.setIsCompleted(goalDTO.getIsCompleted());

        FinancialGoal updatedGoal = financialGoalRepository.save(goal);

        financialGoalRepository.deleteById(id);

        return new CompleteGoalDTO(
                updatedGoal.getId(),
                updatedGoal.getUser().getId(),
                updatedGoal.getIsCompleted());
    }

    // @Scheduled(cron = "0 0 0 * * *")
    @Scheduled(initialDelay = 1)
    public void updateOverdueStatusForGoals() {
        LocalDate currentDate = LocalDate.now();

        List<FinancialGoal> goals = financialGoalRepository.findAll();
        for (FinancialGoal goal : goals) {
            goal.updateOverdueStatus(currentDate);
            financialGoalRepository.save(goal);
        }
    }

    public void deleteGoal(User currentUser, Integer id) {
        FinancialGoal goal = financialGoalRepository.findByUserAndId(currentUser, id)
                .orElseThrow(() -> new IllegalArgumentException("Goal not found: " + id));

        goal.setUpdatedAt(today);
        goal.setDeletedAt(today);

        financialGoalRepository.deleteById(id);

    }

}
