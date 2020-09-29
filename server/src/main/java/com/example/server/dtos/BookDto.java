package com.example.server.dtos;

public interface BookDto {
    // book fields
    Long getId();
    String getTitle();
    String getDescription();
    String getImageUrl();

    // author fields
    Long getAuthorId();
    String getAuthorName();

}
