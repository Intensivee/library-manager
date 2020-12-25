package com.example.server.security.util;

public enum UserPermission {

    BOOK_READ("book:read"),
    BOOK_WRITE("book:write"),
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    CATEGORY_WRITE("category:write"),
    COPY_WRITE("copy:write");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
