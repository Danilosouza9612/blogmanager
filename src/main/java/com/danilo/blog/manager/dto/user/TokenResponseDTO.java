package com.danilo.blog.manager.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TokenResponseDTO {
    private String token;
    private Instant validUntil;
}
