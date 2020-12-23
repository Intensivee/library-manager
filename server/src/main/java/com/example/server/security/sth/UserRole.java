package com.example.server.security.sth;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.server.security.sth.UserPermission.*;

public enum UserRole {

    USER(Sets.newHashSet()),
    AUTHORIZED_USER(Sets.newHashSet(BOOK_READ, BOOK_WRITE)),
    ADMIN(Sets.newHashSet(BOOK_READ, BOOK_WRITE, USER_READ, USER_WRITE, CATEGORY_WRITE, COPY_WRITE));

    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> authorities = this.permissions
                .stream()
                .map(perm -> new SimpleGrantedAuthority(perm.getPermission()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name())); // role is authority with prefix ROLE_
        return authorities;
    }

}
