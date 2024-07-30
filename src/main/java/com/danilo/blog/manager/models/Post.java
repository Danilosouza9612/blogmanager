package com.danilo.blog.manager.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Post extends BlogContent{
    @ManyToOne
    private Category category;

    @ManyToOne
    private User author;
}
