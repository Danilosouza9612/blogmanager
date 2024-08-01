package com.danilo.blog.manager.dto.user;

import java.time.LocalDateTime;

public record SignupResponseDTO(String name, String username, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
