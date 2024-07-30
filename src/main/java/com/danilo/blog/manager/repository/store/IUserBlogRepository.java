package com.danilo.blog.manager.repository.store;

import com.danilo.blog.manager.models.UserBlog;
import com.danilo.blog.manager.models.UserBlogId;
import com.danilo.blog.manager.models.UserBlogRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUserBlogRepository extends JpaRepository<UserBlog, UserBlogId> {
    boolean existsByUserBlogIdAndRole(UserBlogId id, UserBlogRole role);

    @Query("select case when count(ub) > 0 then true else false end from UserBlog ub inner join ub.userBlogId.blog b inner join b.pages p where p.id = ?1 and userBlogId.user.id = ?2 and ub.role = ?3")
    boolean existsByPageIdAndRole(long pageId, long userId, UserBlogRole role);

    @Query("select case when count(ub) > 0 then true else false end from UserBlog ub inner join ub.userBlogId.blog b inner join b.categories c where c.id = ?1 and userBlogId.user.id = ?2 and ub.role = ?3")
    boolean existsByCategoryIdAndRole(long categoryId, long userId, UserBlogRole role);

    @Query("select case when count(ub) > 0 then true else false end from UserBlog ub inner join ub.userBlogId.blog b inner join b.posts p where p.id = ?1 and userBlogId.user.id = ?2 and ub.role = ?3")
    boolean existsByPostIdAndRole(long postId, long userId, UserBlogRole role);
}
