package com.danilo.blog.manager.security;

import com.danilo.blog.manager.models.Blog;
import com.danilo.blog.manager.models.UserBlogRole;
import org.springframework.security.core.GrantedAuthority;

public class BlogGrantedAuthority implements GrantedAuthority {
    private final Blog blog;
    private final UserBlogRole blogRole;

    public BlogGrantedAuthority(Blog blog, UserBlogRole blogRole){
        this.blog = blog;
        this.blogRole = blogRole;
    }
    @Override
    public String getAuthority() {
        return new StringBuilder()
                .append("BLOG_")
                .append(blog.getId())
                .append("_")
                .append(blogRole.toString())
                .toString();
    }
}
