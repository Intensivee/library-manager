package com.example.server.exception;

public class CopyNotFoundException extends RuntimeException {

    private static final String COPY_NOT_FOUND_ERROR_MESSAGE = "Could not find copy with specified id: ";

    public CopyNotFoundException(Long id) {
        super(COPY_NOT_FOUND_ERROR_MESSAGE + id);
    }
}
