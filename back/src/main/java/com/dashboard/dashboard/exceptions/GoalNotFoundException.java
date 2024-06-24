package com.dashboard.dashboard.exceptions;

public class GoalNotFoundException extends RuntimeException {
    public GoalNotFoundException() {
        super("Exception not found");
    }

    public GoalNotFoundException(String message) {
        super(message);
    }

}
