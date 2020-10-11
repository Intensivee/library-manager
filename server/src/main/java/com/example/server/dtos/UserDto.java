package com.example.server.dtos;

import java.util.List;

public class UserDto implements UserProjection {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private int role;
    private List<CopyDto> copies;

    public UserDto(Long id, String username, String firstName, String lastName, String email, int role, List<CopyDto> copies) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.copies = copies;
    }

    public UserDto(UserProjection projection, List<CopyDto> copies) {
        this.id = projection.getId();
        this.username = projection.getUsername();
        this.firstName = projection.getFirstName();
        this.lastName = projection.getLastName();
        this.email = projection.getEmail();
        this.role = projection.getRole();
        this.copies = copies;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public List<CopyDto> getCopies() {
        return copies;
    }

    public void setCopies(List<CopyDto> copies) {
        this.copies = copies;
    }
}
