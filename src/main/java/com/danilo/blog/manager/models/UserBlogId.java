package com.danilo.blog.manager.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class UserBlogId implements Serializable {
    @ManyToOne
    private User user;

    @ManyToOne
    private Blog blog;

    public UserBlogId(){}

    public UserBlogId(User user, Blog blog){
        this.user = user;
        this.blog = blog;
    }
}
