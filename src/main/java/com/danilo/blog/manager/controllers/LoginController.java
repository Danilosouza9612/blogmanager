package com.danilo.blog.manager.controllers;

import com.danilo.blog.manager.dto.user.SigninRequestDTO;
import com.danilo.blog.manager.dto.user.SignupRequestDTO;
import com.danilo.blog.manager.dto.user.SignupResponseDTO;
import com.danilo.blog.manager.dto.user.TokenResponseDTO;
import com.danilo.blog.manager.models.User;
import com.danilo.blog.manager.service.store.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Autowired
    private LoginService service;

    @PostMapping("/signin")
    public ResponseEntity<TokenResponseDTO> signin(@RequestBody @Valid SigninRequestDTO signinRequestDTO){
        User user = new User();
        user.setPassword(signinRequestDTO.getPassword());
        user.setUsername(signinRequestDTO.getUsername());

        return new ResponseEntity<>(this.service.signin(user), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signup(@RequestBody @Valid SignupRequestDTO signupRequestDTO){
        User user = new User();
        user.setName(signupRequestDTO.getName());
        user.setUsername(signupRequestDTO.getUsername());
        user.setEmail(signupRequestDTO.getEmail());
        user.setPassword(signupRequestDTO.getPassword());

        return new ResponseEntity<>(buildDtoFromInstance(this.service.signup(user)), HttpStatus.CREATED);
    }

    private SignupResponseDTO buildDtoFromInstance(User user){
        return new SignupResponseDTO(user.getName(), user.getUsername(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
    }
}
