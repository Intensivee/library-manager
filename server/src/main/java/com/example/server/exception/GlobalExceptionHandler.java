package com.example.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ BookNotFoundException.class,
                        CategoryNotFoundException.class,
                        UserNotFoundException.class,
                        CopyNotFoundException.class})
    public final ResponseEntity<?> notFoundHandler(Exception e){
        Map<String, Object> body = this.createBody(e);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UserOwnsCopiesDeleteException.class,
                        CategoryOwnsBooksDeleteException.class,
                        ObjectCreateException.class})
    public final ResponseEntity<?> conflictHandler(Exception e){
        Map<String, Object> body = this.createBody(e);
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    private Map<String, Object> createBody(Exception e){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", e.toString());
        return body;
    }
}
