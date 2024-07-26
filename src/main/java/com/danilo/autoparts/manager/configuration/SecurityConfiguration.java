package com.danilo.autoparts.manager.configuration;

import com.danilo.autoparts.manager.security.UserAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        (authorize) ->
                                authorize.requestMatchers(HttpMethod.GET, "/api/users").authenticated()
                                        .requestMatchers(HttpMethod.GET, "/api/users/*").authenticated()
                                        .requestMatchers(HttpMethod.POST, "/api/users").authenticated()
                                        .requestMatchers(HttpMethod.PUT, "/api/users/*").authenticated()
                                        .requestMatchers(HttpMethod.DELETE, "/api/users/*").authenticated()
                                        .requestMatchers(HttpMethod.POST, "/api/login/signup").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/api/login/signin").permitAll()

                                        .requestMatchers(HttpMethod.GET, "/api/blogs").authenticated()
                                        .requestMatchers(HttpMethod.POST, "/api/blogs").authenticated()
                                        .requestMatchers("/error").permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
