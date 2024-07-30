package com.danilo.blog.manager.dto.post;

import com.danilo.blog.manager.dto.DisplayDTO;
import com.danilo.blog.manager.dto.blogContent.BlogContentRequestDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostRequestDTO extends BlogContentRequestDTO {
    @NotNull
    private Long categoryId;

    private List<PostCreateTagDTO> tags;
}
