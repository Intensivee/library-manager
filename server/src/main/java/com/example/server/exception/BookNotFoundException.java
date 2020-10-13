package com.example.server.exception;

public class BookNotFoundException extends RuntimeException{

    private static final String BOOKS_ERROR_MESSAGE = "Could not find any books";
    private static final String SINGLE_ID_BOOK_ERROR_MESSAGE = "Could not find book with specified id: ";
    private static final String SINGLE_TITLE_BOOK_ERROR_MESSAGE = "Could not find book with specified title: ";

    public BookNotFoundException() {
        super(BOOKS_ERROR_MESSAGE);
    }

    public BookNotFoundException(Long id) {
        super(SINGLE_ID_BOOK_ERROR_MESSAGE + id);
    }

    public BookNotFoundException(String title) {
        super(SINGLE_TITLE_BOOK_ERROR_MESSAGE + title);
    }
}
