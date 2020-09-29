package com.example.server.exception;

public class BookNotFoundException extends RuntimeException{

    private static final String manyBooksErrorMessage = "Could not find any books";
    private static final String singleBookErrorMessage = "Could not find book with specified id: ";

    public BookNotFoundException() {
        super(manyBooksErrorMessage);
    }

    public BookNotFoundException(Long id) {
        super(singleBookErrorMessage + id);
    }
}
