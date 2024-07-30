package com.danilo.blog.manager.repository.store;

import com.danilo.blog.manager.models.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE (:term is null or p.content like %:term% or p.title like %:term%) and (:blogId is null or p.blog.id = :blogId) and (:categoryId is null or p.category.id = :categoryId) and(:authorId is null or p.author.id = :authorId")
    List<Post> findAllByParameters(
            @Param("term") String term,
            @Param("blogId") Long blogId,
            @Param("categoryId") Long categoryId,
            @Param("authorId") Long authorId,
            Pageable pageable
    );
}
