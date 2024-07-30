package com.danilo.blog.manager.controllers;

import com.danilo.blog.manager.dto.category.CategoryRequestCreateDTO;
import com.danilo.blog.manager.dto.category.CategoryRequestDTO;
import com.danilo.blog.manager.dto.category.CategoryResponseDTO;
import com.danilo.blog.manager.models.Category;
import com.danilo.blog.manager.service.store.BlogService;
import com.danilo.blog.manager.service.store.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceNotFoundException;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService service;
    @Autowired
    private BlogService blogService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> show(@PathVariable("id")long id){
        return service.read(id).map(
                category -> new ResponseEntity<>(
                        new CategoryResponseDTO(category.getId(), category.getName()),
                        HttpStatus.OK
                )
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("permissionByBlog(@policyByBlogImp, #categoryRequestDTO, 'BLOG_ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(@RequestBody CategoryRequestCreateDTO categoryRequestDTO){
        Category category = this.service.create(
                new Category(
                        blogService.getReferenceById(categoryRequestDTO.getBlogId()), categoryRequestDTO.getName()
                )
        );
        return new ResponseEntity<>(
                new CategoryResponseDTO(
                        category.getId(),
                        category.getName()
                ),
                HttpStatus.CREATED
        );
    }

    @PreAuthorize("permissionByInstance(@policyByCategory, #id, 'BLOG_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(@PathVariable("id") long id, @RequestBody @Valid CategoryRequestDTO categoryRequestDTO) throws InstanceNotFoundException {
        Optional<Category> categoryOptional = this.service.read(id);
        if(categoryOptional.isPresent()){
            Category category = categoryOptional.get();
            category.setName(categoryRequestDTO.getName());
            category = this.service.update(
                    category
            );

            return new ResponseEntity<>(
                    new CategoryResponseDTO(
                            category.getId(),
                            category.getName()
                    ),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("permissionByInstance(@policyByCategory, #id, 'BLOG_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        this.service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
