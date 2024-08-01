package com.danilo.blog.manager.service;

import com.danilo.blog.manager.models.Post;
import com.danilo.blog.manager.repository.db.IPostRepository;
import com.danilo.blog.manager.repository.db.IUserRepository;
import com.danilo.blog.manager.security.AppUsernamePasswordAuthenticationToken;
import com.danilo.blog.manager.utils.Sorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService extends CrudService<Post, Long> {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    public PostService(IPostRepository repository){
        super(repository);
    }

    public List<Post> list(int page, int perPage, Sorter sorter, String term, Long blogId, Long categoryId, Long authorId){
        return ((IPostRepository) repository).findAllByParameters(term, blogId, categoryId, authorId, PageRequest.of(page, perPage));
    }

    @Override
    public Post create(Post post){
        AppUsernamePasswordAuthenticationToken authentication = (AppUsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        post.setAuthor(userRepository.getReferenceById(authentication.getId()));
        return super.create(post);
    }
}
