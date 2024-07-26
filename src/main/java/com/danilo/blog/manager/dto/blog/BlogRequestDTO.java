package com.danilo.blog.manager.dto.blog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogRequestDTO {
    @NotNull
    @NotBlank
    @Size(max = 40)
    private String name;

    @NotNull
    @NotBlank
    @Size(max = 20)
    private String slug;

    @NotNull
    @NotBlank
    @Size(max = 255)
    private String description;
}
