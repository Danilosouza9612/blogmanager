package com.danilo.blog.manager.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Post extends BlogContent{
    @ManyToOne
    private Category category;

    @ManyToOne
    private User author;

    @ManyToMany(
            cascade = {CascadeType.PERSIST}
    )
    @JoinTable(
            name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;
}
