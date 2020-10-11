package com.example.server.exception;

public class UserNotFoundException extends RuntimeException {

    private static final String USERS_ERROR_MESSAGE = "Could not find any users";

    public UserNotFoundException() {
        super(USERS_ERROR_MESSAGE);
    }
}
