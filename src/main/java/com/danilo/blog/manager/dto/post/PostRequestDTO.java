package com.danilo.blog.manager.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostRequestDTO {
    @NotNull
    @NotBlank
    @Size(max = 255)
    private String title;

    @NotBlank
    @NotNull
    private String content;

    @NotNull
    private Long categoryId;

    private List<PostCreateTagDTO> tags;
}
