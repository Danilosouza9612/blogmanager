package com.danilo.blog.manager.security;

import com.danilo.blog.manager.models.UserBlogRole;
import com.danilo.blog.manager.models.UserRole;
import com.danilo.blog.manager.policy.BloggablePolicy;
import com.danilo.blog.manager.policy.PolicyByBlog;
import com.danilo.blog.manager.policy.PolicyByInstance;
import com.danilo.blog.manager.service.UserBlogService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class BlogPermissionExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {
    @Getter
    @Setter
    private Object filterObject;
    @Getter
    @Setter
    private Object returnObject;

    public BlogPermissionExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean permissionByBlog(PolicyByBlog policy, long id, UserBlogRole blogRole){
        if(hasRootPermission()) return true;
        return policy.verifyByBlogIdAndUserId(this.getUserById(), id, blogRole);

    }

    public boolean permissionByBlog(PolicyByBlog policy, BloggablePolicy bloggablePolicy, UserBlogRole blogRole){
        if(hasRootPermission()) return true;
        return policy.verifyByBlogIdAndUserId(this.getUserById(), bloggablePolicy.getBlogId(), blogRole);
    }

    public boolean permissionByInstance(PolicyByInstance policy, long id, UserBlogRole blogRole){
        if(hasRootPermission()) return true;
        return policy.verifyByInstanceIdAndUserId(this.getUserById(), id, blogRole);
    }

    @Override
    public Object getThis() {
        return this;
    }

    private long getUserById(){
        return ((AppUsernamePasswordAuthenticationToken) this.getAuthentication()).getId();
    }

    private boolean hasRootPermission(){
        return ((AppUsernamePasswordAuthenticationToken) this.getAuthentication()).getAuthorities().contains(
                new SimpleGrantedAuthority(UserRole.ROOT.toString())
        );
    }
}
