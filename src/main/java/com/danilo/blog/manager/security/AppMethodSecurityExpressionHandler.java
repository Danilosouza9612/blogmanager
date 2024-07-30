package com.danilo.blog.manager.security;

import com.danilo.blog.manager.service.store.UserBlogService;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.AccessException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

import java.util.function.Supplier;

public class AppMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {
    @Override
    public EvaluationContext createEvaluationContext(Supplier<Authentication> authenticationSupplier, MethodInvocation mi){
        StandardEvaluationContext context = (StandardEvaluationContext) super.createEvaluationContext(authenticationSupplier, mi);
        MethodSecurityExpressionOperations methodSecurityExpressionOperations = (MethodSecurityExpressionOperations) context.getRootObject().getValue();
        BlogPermissionExpressionRoot root = new BlogPermissionExpressionRoot(methodSecurityExpressionOperations.getAuthentication());
        try {
            root.setUserBlogService((UserBlogService) getBeanResolver().resolve(context, "userBlogService"));
            context.setRootObject(root);
            return context;
        } catch (AccessException e) {
            throw new RuntimeException(e);
        }
    }
}
