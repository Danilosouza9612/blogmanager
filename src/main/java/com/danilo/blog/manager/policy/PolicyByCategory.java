package com.danilo.blog.manager.policy;

import com.danilo.blog.manager.models.UserBlogRole;
import com.danilo.blog.manager.repository.store.IUserBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PolicyByCategory implements PolicyByInstance{
    @Autowired
    private IUserBlogRepository userBlogRepository;

    @Override
    public boolean verifyByInstanceIdAndUserId(long userId, long instanceId, UserBlogRole role) {
        return this.userBlogRepository.existsByCategoryIdAndRole(instanceId, userId, role);
    }
}
