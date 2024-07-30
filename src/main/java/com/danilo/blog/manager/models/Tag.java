package com.danilo.blog.manager.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Tag{
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 20)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Post> posts;

    public Tag(String name){
        this.name=name;
    }
}
