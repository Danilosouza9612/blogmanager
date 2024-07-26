package com.danilo.blog.manager.security;


import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class AppUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final long id;
    public AppUsernamePasswordAuthenticationToken(
            Object principal,
            long id,
            Object credentials,
            Collection<? extends GrantedAuthority> authorities
    ){
        super(principal, credentials, authorities);
        this.id = id;
    }
}
