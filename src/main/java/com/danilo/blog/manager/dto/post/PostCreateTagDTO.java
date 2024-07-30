package com.danilo.blog.manager.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCreateTagDTO {
    private Long id;

    @NotNull
    @NotBlank
    private String name;
}
