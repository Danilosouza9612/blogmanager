package com.danilo.blog.manager.controllers;

import com.danilo.blog.manager.dto.DisplayDTO;
import com.danilo.blog.manager.dto.post.*;
import com.danilo.blog.manager.models.Post;
import com.danilo.blog.manager.models.Tag;
import com.danilo.blog.manager.service.BlogService;
import com.danilo.blog.manager.service.CategoryService;
import com.danilo.blog.manager.service.PostService;
import com.danilo.blog.manager.service.TagService;
import com.danilo.blog.manager.utils.Sorter;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private TagService tagService;

    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> list(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "per_page", defaultValue = "3") int perPage,
            @RequestParam(value = "sort_column", defaultValue = "createdAt") String sortColumn,
            @RequestParam(value = "sort_direction", defaultValue = "desc") String sortDirection,
            @RequestParam(value = "term", required = false) String term,
            @RequestParam(value = "blog_id", required = false) Long blogId,
            @RequestParam(value = "category_id", required = false) Long categoryId,
            @RequestParam(value = "author_id", required = false) Long authorId
    ){
        List<PostResponseDTO> postResponseDTOList = this.postService.list(
                page,
                perPage,
                new Sorter(sortColumn, sortDirection),
                term,
                blogId,
                categoryId,
                authorId
        ).stream()
                .map(this::buildResponseDTOFromInstance)
                .toList();
        return new ResponseEntity<>(postResponseDTOList, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> show(@PathVariable("id") long id){
        return postService.read(id).map(
                post -> new ResponseEntity<>(this.buildResponseDTOFromInstance(post), HttpStatus.OK)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("permissionByBlog(@policyByBlogImp, #postRequestCreateDTO, 'BLOG_ADMIN')")
    @PostMapping
    public ResponseEntity<PostResponseDTO> create(@RequestBody @Valid PostRequestCreateDTO postRequestCreateDTO){
        return new ResponseEntity<>(
                this.buildResponseDTOFromInstance(
                        this.postService.create(this.buildPostFromPostRequestCreateDTO(postRequestCreateDTO))
                ),
                HttpStatus.CREATED
        );
    }

    @PreAuthorize("permissionByInstance(@policyByPost, #id, 'BLOG_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDTO> update(@PathVariable("id") long id, @RequestBody @Valid PostRequestDTO postRequestDTO) throws InstanceNotFoundException {
        Optional<Post> postOptional = postService.read(id);
        Post post;
        if(postOptional.isPresent()){
            post = postOptional.get();
            this.setPostFromDTO(post, postRequestDTO);
            return new ResponseEntity<>(
                    this.buildResponseDTOFromInstance(
                            this.postService.update(post)
                    ),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("permissionByInstance(@policyByPost, #id, 'BLOG_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id){
        postService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void setPostFromDTO(Post post, PostRequestDTO postRequestDTO){
        post.setTitle(postRequestDTO.getTitle());
        post.setContent(postRequestDTO.getContent());
        post.setTags(postRequestDTO.getTags().stream().map(this::tagMapperFromDto).toList());
        post.setCategory(this.categoryService.getReferenceById(postRequestDTO.getCategoryId()));
    }

    private PostResponseDTO buildResponseDTOFromInstance(Post post){
        return new PostResponseDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                new DisplayDTO(post.getCategory().getId(), post.getCategory().getName()),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                new DisplayDTO(post.getAuthor().getId(), post.getAuthor().getName()),
                post.getTags().stream().map(this::dtoMapperFromTag).toList()
        );
    }

    private Post buildPostFromPostRequestCreateDTO(PostRequestCreateDTO dto){
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setBlog(this.blogService.getReferenceById(dto.getBlogId()));
        post.setCategory(this.categoryService.getReferenceById(dto.getCategoryId()));
        post.setTags(dto.getTags().stream().map(this::tagMapperFromDto).toList());

        LoggerFactory.getLogger(PostController.class).info(post.getContent());

        return post;
    }

    private Tag tagMapperFromDto(PostCreateTagDTO postCreateTagDTO){
        return postCreateTagDTO.getId()==null ? new Tag(postCreateTagDTO.getName()) : tagService.getReferenceById(postCreateTagDTO.getId());
    }

    private DisplayDTO dtoMapperFromTag(Tag tag){
        return new DisplayDTO(tag.getId(), tag.getName());
    }
}
