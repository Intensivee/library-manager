package com.example.server.dtos;

public interface UserProjection {
    Long getId();
    String getUsername();
    String getFirstName();
    String getLastName();
    String getEmail();
    int getRole();
}
