package com.example.server.exception;

public class CategoryNotFoundException extends RuntimeException {

    private static final String CATEGORY_NOT_FOUND = "Could not find category with specified id.";
    private static final String BOOK_CATEGORIES_NOT_FOUND = "Could not find categories of book with specified id: ";

    public CategoryNotFoundException(Long bookId) {
        super(BOOK_CATEGORIES_NOT_FOUND + bookId);
    }

    public CategoryNotFoundException() {
        super(CATEGORY_NOT_FOUND);
    }
}
