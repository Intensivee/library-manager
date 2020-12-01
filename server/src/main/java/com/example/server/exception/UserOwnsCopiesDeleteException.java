package com.example.server.exception;

public class UserOwnsCopiesDeleteException extends RuntimeException {

    private static final String SINGLE_ID_USER_ERROR_MESSAGE = "User with specified id: %d did not " +
            "return all copies. Due to that, Copies that he possess or user himself can not be deleted.";

    public UserOwnsCopiesDeleteException(Long userId) {
        super(String.format(SINGLE_ID_USER_ERROR_MESSAGE, userId));
    }
}
