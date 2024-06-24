package com.dashboard.dashboard.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class LoginUserDTO {

    @Schema(description = "Registered user email")
    @Email
    @NotBlank
    private String email;

    @Schema(description = "Registered user password")
    @NotEmpty
    private String password;

    public LoginUserDTO() {
    }

    public LoginUserDTO(@Email @NotBlank String email, @NotEmpty String password) {
        this.email = email;
        this.password = password;
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

}
