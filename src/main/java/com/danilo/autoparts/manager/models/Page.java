package com.danilo.autoparts.manager.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Page{
    @Id
    @GeneratedValue
    private long id;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    @ManyToOne
    private Blog blog;
}
