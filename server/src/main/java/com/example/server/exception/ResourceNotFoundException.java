package com.example.server.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource, String fieldName, Object fieldValue) {
        super(String.format("%s with %s = '%s' not found.", resource, fieldName, fieldValue));
    }

    public ResourceNotFoundException(String resources) {
        super(String.format("Could not find any %s.", resources));
    }

}
