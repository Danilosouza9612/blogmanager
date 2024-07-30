package com.danilo.blog.manager.security;

import com.danilo.blog.manager.models.Blog;
import com.danilo.blog.manager.models.User;
import com.danilo.blog.manager.models.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.jaas.JaasGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class AppUserDetails implements UserDetails {
    private final User user;

    public AppUserDetails(User user){
        this.user = user;
    }

    public long getId(){
        return this.user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().toString()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
