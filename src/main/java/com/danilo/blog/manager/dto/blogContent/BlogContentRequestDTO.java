package com.danilo.blog.manager.dto.blogContent;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogContentRequestDTO {
    @NotNull
    @NotBlank
    @Size(max = 255)
    private String title;

    @NotBlank
    @NotNull
    private String content;
}
