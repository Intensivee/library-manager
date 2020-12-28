package com.example.server.payload;

import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

public class ExceptionResponse {
    private HttpStatus status;
    private Date timestamp;
    private String message;

    public ExceptionResponse(HttpStatus status, Date timestamp, List<String> messages) {
        this.status = status;
        this.timestamp = timestamp;
        this.message = messages.toString();
    }

    public ExceptionResponse(HttpStatus status, Date timestamp, String message) {
        this.status = status;
        this.timestamp = timestamp;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
