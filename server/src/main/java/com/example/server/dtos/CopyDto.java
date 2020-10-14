package com.example.server.dtos;

import java.util.Date;

public class CopyDto {
    private Long id;
    private int pages;
    private boolean borrowed;
    private Date borrowDate;
    private Date returnDate;

    public CopyDto(Long id, int pages, boolean borrowed, Date borrowDate, Date returnDate) {
        this.id = id;
        this.pages = pages;
        this.borrowed = borrowed;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
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
}
