package com.danilo.autoparts.manager.repository.store;

import com.danilo.autoparts.manager.models.UserBlog;
import com.danilo.autoparts.manager.models.UserBlogId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserBlogRepository extends JpaRepository<UserBlog, UserBlogId> {
}
