package com.example.server.payload;


import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class RegisterRequest {

    @Size(min = 2, max = 20, message = "first name must be between 2 and 20 characters")
    private String firstName;

    @Size(min = 2, max = 30, message = "Last name must be between 2 and 30 characters")
    private String lastName;

    @Email(message = "Provided email is not valid.")
    private String email;

    @Size(min = 5, max = 250, message = "Password must be between 2 and 250 characters")
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
