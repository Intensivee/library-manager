package com.example.server.dtos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class CopyDto {
    private Long id;
    @Min(1)
    private Integer pages;
    @NotNull(message = "borrowed must be specified")
    private Boolean borrowed;
    private Date borrowDate;
    private Date returnDate;
    @NotNull(message = "bookId must be specified")
    private Long bookId;
    private Long userId;

    public CopyDto(Long id, int pages, boolean borrowed, Date borrowDate, Date returnDate, Long bookId, Long userId) {
        this.id = id;
        this.pages = pages;
        this.borrowed = borrowed;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.bookId = bookId;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Boolean getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(Boolean borrowed) {
        this.borrowed = borrowed;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
