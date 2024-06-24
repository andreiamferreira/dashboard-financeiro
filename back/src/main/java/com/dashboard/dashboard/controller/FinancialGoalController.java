package com.dashboard.dashboard.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dashboard.dashboard.dto.CompleteGoalDTO;
import com.dashboard.dashboard.dto.CreateGoalDTO;
import com.dashboard.dashboard.dto.FinancialGoalDTO;
import com.dashboard.dashboard.exceptions.GoalNotFoundException;
import com.dashboard.dashboard.model.User;
import com.dashboard.dashboard.service.FinancialGoalService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Financial Goals", description = "Financial goals routes")
@SecurityRequirement(name = "bearerAuth")
@Validated
@RestController
@RequestMapping("/goals")

public class FinancialGoalController {
    @Autowired
    private FinancialGoalService goalService;

    @GetMapping
    public List<FinancialGoalDTO> getAllGoalsByUser(@AuthenticationPrincipal User currentUser) {
        return goalService.getAllGoals(currentUser.getId());
    }

    @GetMapping("/{goalId}")
    public ResponseEntity<FinancialGoalDTO> getGoalById(@AuthenticationPrincipal User currentUser,
            @PathVariable Integer goalId) {
        FinancialGoalDTO goal = goalService.getGoalById(currentUser.getId(), goalId);

        if (Objects.isNull(goal)) {
            throw new GoalNotFoundException();
        }

        return ResponseEntity.ok(goal);
    }

    @GetMapping("/closer")
    public ResponseEntity<List<FinancialGoalDTO>> getCloserLimitDate(@AuthenticationPrincipal User currentUser) {
        List<FinancialGoalDTO> mostRecents = goalService.getCloserLimitDate(currentUser.getId());

        return ResponseEntity.ok(mostRecents);
    }

    @PostMapping("/add")
    public ResponseEntity<CreateGoalDTO> addGoal(@AuthenticationPrincipal User currentUser,
            @RequestBody CreateGoalDTO goalDTO) {

        CreateGoalDTO newGoal = goalService.addGoal(currentUser, goalDTO);

        return new ResponseEntity<>(newGoal, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<FinancialGoalDTO> updateGoal(@AuthenticationPrincipal User currentUser,
            @PathVariable Integer id,
            @RequestBody FinancialGoalDTO goalDTO) {
        FinancialGoalDTO goal = goalService.updateGoal(currentUser, id, goalDTO);

        return new ResponseEntity<>(goal, HttpStatus.OK);
    }

    @PutMapping("/complete-goal/{id}")
    public ResponseEntity<CompleteGoalDTO> completeGoal(@AuthenticationPrincipal User currentUser,
            @PathVariable Integer id,
            @RequestBody CompleteGoalDTO goalDTO) {
        CompleteGoalDTO goal = goalService.completeGoal(currentUser, id, goalDTO);

        return new ResponseEntity<>(goal, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteGoal(@AuthenticationPrincipal User currentUser, @PathVariable Integer id) {
        goalService.deleteGoal(currentUser, id);
    }
}
