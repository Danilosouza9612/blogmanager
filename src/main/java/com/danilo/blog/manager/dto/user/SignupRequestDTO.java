package com.danilo.blog.manager.dto.user;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDTO {
    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    private String confirmPassword;

    @AssertTrue(message = "Password and confirm password must be equal")
    public boolean isPasswordEqualsToConfirmPassword(){
        return password.equals(confirmPassword);
    }
}
