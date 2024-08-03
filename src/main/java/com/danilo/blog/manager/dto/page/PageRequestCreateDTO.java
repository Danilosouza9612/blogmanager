package com.danilo.blog.manager.dto.page;

import com.danilo.blog.manager.policy.BloggablePolicy;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestCreateDTO extends PageRequestDTO implements BloggablePolicy {
    @NotNull
    private Long blogId;
}
