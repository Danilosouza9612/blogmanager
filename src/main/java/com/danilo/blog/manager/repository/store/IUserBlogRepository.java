package com.danilo.blog.manager.repository.store;

import com.danilo.blog.manager.models.UserBlog;
import com.danilo.blog.manager.models.UserBlogId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserBlogRepository extends JpaRepository<UserBlog, UserBlogId> {
}
