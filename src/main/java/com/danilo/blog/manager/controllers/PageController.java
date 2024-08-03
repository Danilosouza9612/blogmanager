package com.danilo.blog.manager.controllers;

import com.danilo.blog.manager.dto.page.PageRequestCreateDTO;
import com.danilo.blog.manager.dto.page.PageRequestDTO;
import com.danilo.blog.manager.dto.page.PageResponseDTO;
import com.danilo.blog.manager.models.Page;
import com.danilo.blog.manager.service.BlogService;
import com.danilo.blog.manager.service.PageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceNotFoundException;
import java.util.Optional;

@RestController
@RequestMapping("/api/pages")
public class PageController {
    @Autowired
    private PageService service;

    @Autowired
    private BlogService blogService;

    @GetMapping("/{id}")
    public ResponseEntity<PageResponseDTO> show(@PathVariable("id") long id){
        return service.read(id).map(page -> new ResponseEntity<>(this.buildResponseDtoFromPage(page), HttpStatus.OK)).orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("permissionByBlog(@policyByBlogImp, #blogContentDTO, 'BLOG_ADMIN')")
    @PostMapping
    public ResponseEntity<PageRequestDTO> create(@RequestBody @Valid PageRequestCreateDTO blogContentDTO){
        return new ResponseEntity<>(this.buildDtoFromPage(this.service.create(this.buildPageFromCreateDTO(blogContentDTO))), HttpStatus.CREATED);
    }

    @PreAuthorize("permissionByInstance(@policyByPage, #id, 'BLOG_ADMIN')")
    @PutMapping
    public ResponseEntity<PageRequestDTO> update(@PathVariable("id") long id, @RequestBody @Valid PageRequestDTO blogContentDTO) throws InstanceNotFoundException {
        Optional<Page> pageOptional = service.read(id);
        Page page;
        if(pageOptional.isPresent()){
            page = pageOptional.get();
            this.setPageFromDto(pageOptional.get(), blogContentDTO);
            return new ResponseEntity<>(this.buildDtoFromPage(this.service.update(page)), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("permissionByInstance(@policyByPage, #id, 'BLOG_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id){
        this.service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Page buildPageFromDTO(PageRequestDTO blogContentRequestDTO){
        Page page = new Page();
        page.setContent(blogContentRequestDTO.getContent());
        page.setTitle(blogContentRequestDTO.getTitle());
        return page;
    }

    private PageRequestDTO buildDtoFromPage(Page page){
        PageRequestDTO blogContentDTO = new PageRequestDTO();

        blogContentDTO.setTitle(page.getTitle());
        blogContentDTO.setContent(page.getContent());

        return blogContentDTO;
    }

    private Page buildPageFromCreateDTO(PageRequestCreateDTO blogContentCreateDTO){
        Page page = this.buildPageFromDTO(blogContentCreateDTO);
        page.setBlog(blogService.getReferenceById(blogContentCreateDTO.getBlogId()));
        return page;
    }

    private PageResponseDTO buildResponseDtoFromPage(Page page){
        return new PageResponseDTO(page.getId(), page.getTitle(), page.getContent());
    }

    private void setPageFromDto(Page page, PageRequestDTO blogContentRequestDTO){
        page.setTitle(blogContentRequestDTO.getTitle());
        page.setContent(blogContentRequestDTO.getContent());
    }
}
