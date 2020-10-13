package com.example.server.exception;

public class UserNotFoundException extends RuntimeException {

    private static final String USERS_ERROR_MESSAGE = "Could not find any users";
    private static final String SINGLE_ID_USER_ERROR_MESSAGE = "Could not find user with specified id: ";

    public UserNotFoundException() {
        super(USERS_ERROR_MESSAGE);
    }

    public UserNotFoundException(Long id){
        super(SINGLE_ID_USER_ERROR_MESSAGE + id);
    }
}
