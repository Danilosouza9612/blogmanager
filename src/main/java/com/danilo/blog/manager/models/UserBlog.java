package com.danilo.blog.manager.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserBlog{
    @EmbeddedId
    private UserBlogId userBlogId;

    @Enumerated(EnumType.ORDINAL)
    private UserBlogRole role;
}
