package com.danilo.blog.manager.security;

import com.danilo.blog.manager.models.User;
import com.danilo.blog.manager.repository.store.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private IUserRepository repository;

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String subject;
        Optional<User> optional;
        User user;
        AppUserDetails userDetails;
        Authentication authentication;

        if(authorization!=null) {
            authorization = authorization.replace("Bearer ", "");
            subject = jwtService.getUser(authorization);
            optional = repository.findByUsername(subject);
            if (optional.isPresent()) {
                user = optional.get();
                userDetails = new AppUserDetails(user);
                authentication = new AppUsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getId(), null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
