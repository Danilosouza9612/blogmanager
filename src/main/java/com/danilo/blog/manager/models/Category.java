package com.danilo.blog.manager.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"blog_id", "name"}))
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Blog blog;

    @Column(length = 100)
    private String name;

    public Category(Blog blog, String name){
        this.blog = blog;
        this.name = name;
    }

    public Category(String name){
        this.name = name;
    }
}
