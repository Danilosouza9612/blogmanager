package com.danilo.blog.manager.dto.post;

import com.danilo.blog.manager.policy.BloggablePolicy;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostRequestCreateDTO extends PostRequestDTO implements BloggablePolicy {
    @NotNull
    private Long blogId;

    private List<PostCreateTagDTO> tags;
}
