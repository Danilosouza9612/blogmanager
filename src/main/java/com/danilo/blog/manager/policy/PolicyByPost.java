package com.danilo.blog.manager.policy;

import com.danilo.blog.manager.models.UserBlogRole;
import com.danilo.blog.manager.repository.db.IUserBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PolicyByPost implements PolicyByInstance{
    @Autowired
    private IUserBlogRepository repository;

    @Override
    public boolean verifyByInstanceIdAndUserId(long userId, long instanceId, UserBlogRole role) {
        return repository.existsByPostIdAndRole(instanceId, userId, role);
    }
}
