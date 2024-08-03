package com.danilo.blog.manager.dto.post;

import com.danilo.blog.manager.dto.DisplayDTO;

import java.time.LocalDateTime;
import java.util.List;

public record PostResponseDTO(
        long id,
        String title,
        String content,
        DisplayDTO category,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        DisplayDTO author,
        List<DisplayDTO> tags
) {
}
