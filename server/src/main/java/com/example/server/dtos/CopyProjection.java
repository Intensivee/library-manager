package com.example.server.dtos;

import java.util.Date;

public interface CopyProjection {
    Long getId();
    int getPages();
    Boolean getBorrowed();
    Date getBorrowDate();
    Date getReturnDate();
}
