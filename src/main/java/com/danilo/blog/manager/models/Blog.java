package com.danilo.blog.manager.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Blog{
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false)
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "userBlogId.blog")
    private List<UserBlog> userBlogs;

    @OneToMany(mappedBy = "blog")
    private List<Page> pages;

    @OneToMany(mappedBy = "blog")
    private List<Category> categories;

    @OneToMany(mappedBy = "blog")
    private List<Post> posts;
}
