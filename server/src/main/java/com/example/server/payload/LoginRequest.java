package com.example.server.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class LoginRequest {

    @Email(message = "Provided email is not valid.")
    private String email;

    @NotBlank(message = "Password must be specified!")
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String email, String password) {
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
