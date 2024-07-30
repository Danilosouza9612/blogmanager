package com.danilo.blog.manager.dto.post;

import com.danilo.blog.manager.dto.DisplayDTO;

import java.time.LocalDateTime;

public record PostResponseDTO(
        String title,
        String content,
        DisplayDTO category,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        DisplayDTO author
) {
}
