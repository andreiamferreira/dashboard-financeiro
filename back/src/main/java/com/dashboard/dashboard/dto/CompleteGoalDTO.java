package com.dashboard.dashboard.dto;

public class CompleteGoalDTO {
    private Integer id;

    private Boolean isCompleted;

    private Integer userId;

    public CompleteGoalDTO() {
    }

    public CompleteGoalDTO(Integer id, Integer userId, Boolean isCompleted) {
        this.id = id;
        this.userId = userId;
        this.isCompleted = isCompleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
