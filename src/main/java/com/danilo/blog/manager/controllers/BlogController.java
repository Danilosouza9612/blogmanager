package com.danilo.blog.manager.controllers;

import com.danilo.blog.manager.dto.blog.BlogRequestDTO;
import com.danilo.blog.manager.dto.blog.BlogResponseDTO;
import com.danilo.blog.manager.dto.blog.BlogUserBlogResponseDTO;
import com.danilo.blog.manager.dto.user.UserResponseDTO;
import com.danilo.blog.manager.models.Blog;
import com.danilo.blog.manager.service.BlogService;
import com.danilo.blog.manager.utils.Sorter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.management.InstanceNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {
    @Autowired
    private BlogService service;

    @GetMapping
    public ResponseEntity<Iterable<BlogResponseDTO>> list(
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
    public ResponseEntity<Blog> create(@Valid @RequestBody BlogRequestDTO blogDTO){
        Blog blog = new Blog();

        blog.setName(blogDTO.getName());
        blog.setSlug(blogDTO.getSlug());
        blog.setDescription(blogDTO.getDescription());

        return new ResponseEntity<>(service.create(blog), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogResponseDTO> show(@PathVariable("id") long id){
        return service.read(id).map(
                blog -> new ResponseEntity<>(this.buildBlogResponseDTO(blog), HttpStatus.OK)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("tenantPermission(#id, 'BLOG_ADMIN')")
    @PutMapping
    public ResponseEntity<BlogResponseDTO> update(@PathVariable("id") long id, @RequestBody BlogRequestDTO blogRequestDTO) throws InstanceNotFoundException {
        Optional<Blog> blogOptional = service.read(id);
        Blog blog;
        if(blogOptional.isPresent()) {
            blog = blogOptional.get();
            blog.setName(blogRequestDTO.getName());
            blog.setDescription(blogRequestDTO.getDescription());
            blog.setSlug(blogRequestDTO.getSlug());

            return new ResponseEntity<>(this.buildBlogResponseDTO(service.update(blog)), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("tenantPermission(#id, 'BLOG_ADMIN')")
    @DeleteMapping
    public ResponseEntity<?> delete(@PathVariable("id") long id){
        service.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("tenantPermission(#id, 'BLOG_ADMIN')")
    @PostMapping("/{id}/uploadFile")
    public ResponseEntity<String> uploadFile(@PathVariable("id") long id, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        Optional<Blog> blogOptional = service.read(id);
        if(blogOptional.isPresent()){
            return new ResponseEntity<>(service.uploadFile(blogOptional.get(), multipartFile), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("tenantPermission(#id, 'BLOG_ADMIN')")
    @DeleteMapping("/{id}/deleteFile/{identifier}")
    public ResponseEntity<?> deleteFile(@PathVariable("id") long id, @PathVariable("identifier") String identifier){
        Optional<Blog> blogOptional = service.read(id);
        if(blogOptional.isPresent()){
            service.deleteFile(blogOptional.get(), identifier);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
