package com.danilo.autoparts.manager.controllers;

import com.danilo.autoparts.manager.dto.blog.BlogRequestDTO;
import com.danilo.autoparts.manager.dto.blog.BlogResponseDTO;
import com.danilo.autoparts.manager.dto.blog.BlogUserBlogResponseDTO;
import com.danilo.autoparts.manager.dto.user.UserResponseDTO;
import com.danilo.autoparts.manager.models.Blog;
import com.danilo.autoparts.manager.models.UserBlog;
import com.danilo.autoparts.manager.service.store.BlogService;
import com.danilo.autoparts.manager.utils.Sorter;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {
    @Autowired
    private BlogService service;

    @GetMapping
    private ResponseEntity<Iterable<BlogResponseDTO>> list(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "per_page", defaultValue = "3") int perPage,
            @RequestParam(value = "sort_column", defaultValue = "createdAt") String sortColumn,
            @RequestParam(value = "sort_direction", defaultValue = "desc") String sortDirection
    ){

        return new ResponseEntity<>(StreamSupport.stream(service.list(page, perPage, new Sorter(sortColumn, sortDirection)).spliterator(), false)
                .map(this::buildBlogResponseDTO).toList(),
                HttpStatus.OK
        );
    }

    @PostMapping
    private ResponseEntity<Blog> create(@Valid @RequestBody BlogRequestDTO blogDTO){
        Blog blog = new Blog();

        blog.setName(blogDTO.getName());
        blog.setSlug(blogDTO.getSlug());
        blog.setDescription(blogDTO.getDescription());

        return new ResponseEntity<>(service.create(blog), HttpStatus.CREATED);
    }

    private BlogResponseDTO buildBlogResponseDTO(Blog blog){
        return new BlogResponseDTO(
                blog.getId(),
                blog.getName(),
                blog.getSlug(),
                blog.getDescription(),
                blog.getCreatedAt(),
                blog.getUpdatedAt(),
                blog.getUserBlogs().stream().map(
                        userBlog -> new BlogUserBlogResponseDTO(
                                new UserResponseDTO(
                                        userBlog.getUserBlogId().getUser().getId(),
                                        userBlog.getUserBlogId().getUser().getName(),
                                        userBlog.getUserBlogId().getUser().getEmail(),
                                        userBlog.getUserBlogId().getUser().getUsername()
                                ),
                                userBlog.getRole()
                        )
                ).toList()
        );
    }
}
