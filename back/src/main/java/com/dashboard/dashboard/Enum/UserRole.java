package com.dashboard.dashboard.Enum;

public enum UserRole {

    ADMIN("admin"),

    USER("user"),

    VIEWER("viewer");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
