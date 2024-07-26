package com.danilo.blog.manager.dto.blog;

import com.danilo.blog.manager.dto.user.UserResponseDTO;
import com.danilo.blog.manager.models.UserBlogRole;

public record BlogUserBlogResponseDTO(UserResponseDTO user, UserBlogRole role){}
