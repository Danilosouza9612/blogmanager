package com.danilo.autoparts.manager.dto.blog;

import com.danilo.autoparts.manager.dto.user.UserResponseDTO;
import com.danilo.autoparts.manager.models.UserBlogRole;

public record BlogUserBlogResponseDTO(UserResponseDTO user, UserBlogRole role){}
