package com.danilo.blog.manager.dto.post;

import com.danilo.blog.manager.dto.blogContent.BlogContentRequestDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDTO extends BlogContentRequestDTO {
    @NotNull
    private Long categoryId;
}
