package com.danilo.blog.manager.dto.blog;

import java.time.LocalDateTime;
import java.util.List;

public record BlogResponseDTO(long id, String name, String slug, String description, LocalDateTime createdAt, LocalDateTime updatedAt, List<BlogUserBlogResponseDTO> userBlogs){}
