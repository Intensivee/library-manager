package com.example.server.exception;

public class CategoryNotFoundException extends RuntimeException {

    private static final String CATEGORY_NOT_FOUND = "Could not find category with specified id: ";

    public CategoryNotFoundException(Long bookId) {
        super(CATEGORY_NOT_FOUND + bookId);
    }
}
