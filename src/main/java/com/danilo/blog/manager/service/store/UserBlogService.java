package com.danilo.blog.manager.service.store;

import com.danilo.blog.manager.models.UserBlog;
import com.danilo.blog.manager.models.UserBlogId;
import com.danilo.blog.manager.models.UserBlogRole;
import com.danilo.blog.manager.repository.store.IBlogRepository;
import com.danilo.blog.manager.repository.store.IUserBlogRepository;
import com.danilo.blog.manager.repository.store.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userBlogService")
public class UserBlogService extends CrudService<UserBlog, UserBlogId> {
    @Autowired
    private IBlogRepository iBlogRepository;

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    public UserBlogService(IUserBlogRepository repository){
        super(repository);
    }

    public boolean existsByUserBlogIdAndRole(long blogId, long userId, UserBlogRole role){
        UserBlogId userBlogId = new UserBlogId();
        userBlogId.setBlog(iBlogRepository.getReferenceById(blogId));
        userBlogId.setUser(iUserRepository.getReferenceById(userId));

        return ((IUserBlogRepository ) repository).existsByUserBlogIdAndRole(userBlogId, role);
    }
}
