package com.danilo.blog.manager.dto.post;

import com.danilo.blog.manager.dto.blogContent.BlogContentRequestCreateDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestCreateDTO extends BlogContentRequestCreateDTO {
    @NotNull
    private Long categoryId;
}
