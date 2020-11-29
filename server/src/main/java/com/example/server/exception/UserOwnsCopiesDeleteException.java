package com.example.server.exception;

public class UserOwnsCopiesDeleteException extends RuntimeException {

    private static final String SINGLE_ID_USER_ERROR_MESSAGE = "User with specified id: %d, did not return all copies.";

    public UserOwnsCopiesDeleteException(Long id) {
        super(String.format(SINGLE_ID_USER_ERROR_MESSAGE, id));
    }
}
