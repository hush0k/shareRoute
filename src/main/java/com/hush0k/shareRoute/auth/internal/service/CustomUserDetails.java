package com.hush0k.shareRoute.auth.internal.service;

import com.hush0k.shareRoute.auth.internal.domins.Credentials;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
public class CustomUserDetails implements UserDetails {

    private final UUID userId;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean isBlocked;

    public CustomUserDetails(Credentials credentials) {
        this.userId = credentials.getUserId();
        this.email = credentials.getEmail();
        this.password = credentials.getHashedPassword();
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        this.isBlocked = Boolean.TRUE.equals(credentials.getIsBlocked());
    }

    @Override
    @NullMarked
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isBlocked;
    }

}
