package com.danilo.blog.manager.service.store;

import com.danilo.blog.manager.exception.BusinessRuleViolationException;
import com.danilo.blog.manager.exception.ErrorSerialization;
import com.danilo.blog.manager.models.User;
import com.danilo.blog.manager.repository.store.IUserRepository;
import com.danilo.blog.manager.security.AppUserDetails;
import com.danilo.blog.manager.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private IUserRepository repository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    public User signup(User user){
        String hashedPassword;

        this.throwUniqueBusinessRuleViolationExceptionIfUsernameIsNotUnique(user);
        hashedPassword = encoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return repository.save(user);
    }

    public String signin(User user){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();

        return jwtService.generateToken(userDetails);
    }

    private void throwUniqueBusinessRuleViolationExceptionIfUsernameIsNotUnique(User user){
        if(this.repository.findByUsername(user.getUsername()).isPresent()){
            ErrorSerialization errorSerialization = new ErrorSerialization();
            errorSerialization.addError("username", "Este usuário já existe");
            throw new BusinessRuleViolationException(errorSerialization);
        }
    }
}
