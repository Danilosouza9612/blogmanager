package com.danilo.blog.manager.policy;

import com.danilo.blog.manager.models.UserBlogRole;

public interface PolicyByInstance {
    boolean verifyByInstanceIdAndUserId(long userId, long instanceId, UserBlogRole role);
}
