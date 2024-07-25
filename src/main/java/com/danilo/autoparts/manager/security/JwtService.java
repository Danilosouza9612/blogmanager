package com.danilo.autoparts.manager.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtService {
    private final Algorithm algorithm;

    public JwtService(){
        byte[] secretKeysNumber = {125, 120, 20, 40};
        this.algorithm = Algorithm.HMAC256(new SecretKeySpec(secretKeysNumber, "AES").getAlgorithm());
    }

    public String generateToken(AppUserDetails userDetails){
        return JWT.create()
                .withIssuedAt(createdAt())
                .withExpiresAt(validUntil())
                .withSubject(userDetails.getUsername())
                .sign(this.algorithm);
    }

    public String getUser(String token){
        return JWT.require(algorithm)
                .build()
                .verify(token)
                .getSubject();
    }

    private Instant createdAt(){
        return Instant.now();
    }

    private Instant validUntil(){
        return this.createdAt().plus(24, ChronoUnit.HOURS);
    }
}
