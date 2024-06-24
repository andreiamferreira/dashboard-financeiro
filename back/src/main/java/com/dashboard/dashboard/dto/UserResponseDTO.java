package com.dashboard.dashboard.dto;

import com.dashboard.dashboard.Enum.UserRole;

public class UserResponseDTO {

    private Integer id;

    private String name;

    private String cpf;

    private String email;

    private UserRole role;

    public UserResponseDTO() {
    }

    public UserResponseDTO(Integer id, String name, String cpf, String email, UserRole role) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

}
