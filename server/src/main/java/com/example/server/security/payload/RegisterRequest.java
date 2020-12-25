package com.example.server.security.payload;


import javax.validation.constraints.NotBlank;

public class RegisterRequest {

    @NotBlank(message = "First name must be specified!")
    private String firstName;

    @NotBlank(message = "Last name must be specified!")
    private String lastName;

    @NotBlank(message = "Email must be specified!")
    private String email;

    @NotBlank(message = "Password must be specified!")
    private String password;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
