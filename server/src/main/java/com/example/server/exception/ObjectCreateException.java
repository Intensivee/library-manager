package com.example.server.exception;

public class ObjectCreateException extends RuntimeException {

    private static final String ID_IS_NOT_NULL_ERROR_MESSAGE = "Id should be null. Can not create new object with specified id: ";
    private static final String REQUIRED_FIELD_MISSING_ERROR_MESSAGE = "Not all required fields are filled properly";

    public ObjectCreateException() {
        super(REQUIRED_FIELD_MISSING_ERROR_MESSAGE);
    }

    public ObjectCreateException(Long id){
        super(ID_IS_NOT_NULL_ERROR_MESSAGE + id);
    }
}
