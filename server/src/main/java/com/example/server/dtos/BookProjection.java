package com.example.server.dtos;

public interface BookProjection {
    // book fields
    Long getId();
    String getTitle();
    String getDescription();
    String getImageUrl();

    // author fields
    Long getAuthorId();
    String getAuthorName();

}
