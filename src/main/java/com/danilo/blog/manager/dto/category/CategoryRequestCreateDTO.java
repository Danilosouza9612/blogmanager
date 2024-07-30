package com.danilo.blog.manager.dto.category;

import com.danilo.blog.manager.policy.BloggablePolicy;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequestCreateDTO extends CategoryRequestDTO implements BloggablePolicy {
    @NotNull
    private Long blogId;
}
