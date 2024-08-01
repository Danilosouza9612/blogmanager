package com.danilo.blog.manager.service;

import com.danilo.blog.manager.repository.db.IUserRepository;
import com.danilo.blog.manager.security.AppUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService{
    @Autowired
    private IUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .map(AppUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}
