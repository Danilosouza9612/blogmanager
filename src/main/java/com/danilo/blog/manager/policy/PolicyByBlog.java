package com.danilo.blog.manager.policy;

import com.danilo.blog.manager.models.UserBlogRole;

public interface PolicyByBlog {
    boolean verifyByBlogIdAndUserId(long userId, long blogId, UserBlogRole role);
}
