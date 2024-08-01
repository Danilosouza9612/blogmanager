package com.danilo.blog.manager.configuration;

import com.danilo.blog.manager.repository.file.IStorageRepository;
import com.danilo.blog.manager.repository.file.S3StorageRepository;
import com.danilo.blog.manager.security.AppMethodSecurityExpressionHandler;
import com.danilo.blog.manager.security.UserAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
@EnableMethodSecurity
@DependsOn({"userBlogService"})
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

                                        .requestMatchers(HttpMethod.GET, "/api/blogs").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/api/blogs/{id}").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/api/blogs").authenticated()
                                        .requestMatchers(HttpMethod.POST, "/api/blogs/{id}/uploadFile").authenticated()
                                        .requestMatchers(HttpMethod.DELETE, "/api/blogs/{id}/deleteFile/{identifier}").authenticated()

                                        .requestMatchers(HttpMethod.GET, "/api/pages/*").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/api/pages").authenticated()
                                        .requestMatchers(HttpMethod.DELETE, "/api/pages/*").authenticated()
                                        .requestMatchers(HttpMethod.PUT, "/api/pages/*").authenticated()

                                        .requestMatchers(HttpMethod.GET, "/api/categories/*").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/api/categories").authenticated()
                                        .requestMatchers(HttpMethod.PUT, "/api/categories/*").authenticated()
                                        .requestMatchers(HttpMethod.DELETE, "/api/categories/*").authenticated()

                                        .requestMatchers(HttpMethod.GET, "/api/posts/*").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/api/posts").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/api/posts").authenticated()
                                        .requestMatchers(HttpMethod.PUT, "/api/posts/*").authenticated()
                                        .requestMatchers(HttpMethod.DELETE, "/api/posts/*").authenticated()

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

    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler(){
        return new AppMethodSecurityExpressionHandler();
    }
}
