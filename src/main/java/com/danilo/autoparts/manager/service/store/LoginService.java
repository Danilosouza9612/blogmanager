package com.danilo.autoparts.manager.service.store;

import com.danilo.autoparts.manager.exception.BusinessRuleViolationException;
import com.danilo.autoparts.manager.exception.ErrorSerialization;
import com.danilo.autoparts.manager.models.User;
import com.danilo.autoparts.manager.repository.store.IUserRepository;
import com.danilo.autoparts.manager.security.AppUserDetails;
import com.danilo.autoparts.manager.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
