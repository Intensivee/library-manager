package com.example.server.exception;

public class CategoryOwnsBooksDeleteException extends RuntimeException {

    private static final String CATEGORY_OWNS_BOOKS = "Category with id: %d, has assigned books.";

    public CategoryOwnsBooksDeleteException(Long id) {
        super(String.format(CATEGORY_OWNS_BOOKS, id));
    }
}
