package com.danilo.autoparts.manager.repository.store;

import com.danilo.autoparts.manager.models.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBlogRepository extends JpaRepository<Blog, Long> {
    Optional<Blog> findBySlug(String slug);
}
