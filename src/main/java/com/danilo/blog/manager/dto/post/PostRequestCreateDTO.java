package com.danilo.blog.manager.dto.post;

import com.danilo.blog.manager.dto.blogContent.BlogContentRequestCreateDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostRequestCreateDTO extends BlogContentRequestCreateDTO {
    private Long categoryId;

    private List<PostCreateTagDTO> tags;
}
