package com.example.server.exception;

public class ResourceCreateException extends RuntimeException {

    private static final String ID_IS_NOT_NULL_ERROR_MESSAGE = "Id should be null. Can not create new object with specified id: ";
    private static final String REQUIRED_FIELD_MISSING_ERROR_MESSAGE = "Not all required fields are filled properly";

    public ResourceCreateException() {
        super(REQUIRED_FIELD_MISSING_ERROR_MESSAGE);
    }

    public ResourceCreateException(Long id){
        super(String.format("%s %d", ID_IS_NOT_NULL_ERROR_MESSAGE, id));
    }
}
