package com.danilo.blog.manager.policy;

import com.danilo.blog.manager.models.UserBlogId;
import com.danilo.blog.manager.models.UserBlogRole;
import com.danilo.blog.manager.repository.db.IBlogRepository;
import com.danilo.blog.manager.repository.db.IUserBlogRepository;
import com.danilo.blog.manager.repository.db.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PolicyByBlogImp implements PolicyByBlog {
    @Autowired
    private IUserBlogRepository userBlogRepository;

    @Autowired
    private IBlogRepository blogRepository;

    @Autowired
    private IUserRepository userRepository;

    public boolean verifyByBlogIdAndUserId(long userId, long blogId, UserBlogRole role){
        return this.userBlogRepository.existsByUserBlogIdAndRole(
                new UserBlogId(
                        userRepository.getReferenceById(userId),
                        blogRepository.getReferenceById(blogId)
                ),
                role
        );
    }
}
