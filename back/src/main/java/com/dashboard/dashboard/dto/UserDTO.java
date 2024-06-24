package com.dashboard.dashboard.dto;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.hateoas.RepresentationModel;

import com.dashboard.dashboard.Enum.UserRole;
import com.dashboard.dashboard.model.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDTO extends RepresentationModel<UserDTO> {

    private Integer id;

    @CPF
    @NotNull
    private String cpf;

    @NotEmpty
    @Size(min = 1)
    private String name;

    @Email
    @NotBlank
    @Size(min = 1)
    private String email;

    @Size(min = 6)
    @NotEmpty
    private String password;

    private UserRole role;

    public UserDTO() {

    }

    public UserDTO(@NotEmpty @Size(min = 1, max = 100) String name,
            @Email @NotBlank @Size(min = 1, max = 30) String email, @CPF @NotNull String cpf,
            @Size(min = 6) @NotEmpty String password,
            UserRole role) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.password = password;
        this.role = role;
    }

    public UserDTO(User user) {
        cpf = user.getCpf();
        name = user.getName();
        email = user.getEmail();
        password = user.getPassword();
        role = user.getRole();
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
