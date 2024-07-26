package com.danilo.blog.manager.service.store;

import com.danilo.blog.manager.exception.BusinessRuleViolationException;
import com.danilo.blog.manager.exception.ErrorSerialization;
import com.danilo.autoparts.manager.models.*;
import com.danilo.blog.manager.repository.store.IBlogRepository;
import com.danilo.blog.manager.repository.store.IUserBlogRepository;
import com.danilo.blog.manager.repository.store.IUserRepository;
import com.danilo.blog.manager.security.AppUsernamePasswordAuthenticationToken;
import com.danilo.blog.manager.models.Blog;
import com.danilo.blog.manager.models.UserBlog;
import com.danilo.blog.manager.models.UserBlogId;
import com.danilo.blog.manager.models.UserBlogRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogService extends CrudService<Blog, Long> {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IUserBlogRepository iUserBlogRepository;

    @Autowired
    public BlogService(IBlogRepository repository){
        super(repository);
    }

    @Override
    @Transactional
    public Blog create(Blog blog){
        AppUsernamePasswordAuthenticationToken authentication = (AppUsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        List<UserBlog> userBlogs = new ArrayList<>();
        UserBlogId userBlogId = new UserBlogId();
        UserBlog userBlog = new UserBlog();
        Blog createdBlog;

        this.throwUniqueBusinessRuleViolationExceptionIfSlugIsNotUnique(blog);

        createdBlog =  super.create(blog);

        userBlogId.setUser(userRepository.getReferenceById(authentication.getId()));
        userBlogId.setBlog(createdBlog);
        userBlog.setUserBlogId(userBlogId);
        userBlog.setRole(UserBlogRole.BLOG_ADMIN);

        iUserBlogRepository.save(userBlog);

        return createdBlog;
    }

    private boolean slugExists(String username){
        IBlogRepository blogRepository = (IBlogRepository) this.repository;
        return blogRepository.findBySlug(username).isPresent();
    }

    private void throwUniqueBusinessRuleViolationExceptionIfSlugIsNotUnique(Blog blog){
        if(this.slugExists(blog.getSlug())){
            ErrorSerialization errorSerialization = new ErrorSerialization();
            errorSerialization.addError("slug", "Já existe um blog com esse endereço");
            throw new BusinessRuleViolationException(errorSerialization);
        }
    }
}
