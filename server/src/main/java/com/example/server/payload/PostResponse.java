package com.example.server.payload;

import java.net.URI;

public class PostResponse {

    private Long id;
    private URI location;

    public PostResponse() {
    }

    public PostResponse(Long id, URI location) {
        this.id = id;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public URI getLocation() {
        return location;
    }

    public void setLocation(URI location) {
        this.location = location;
    }
}
