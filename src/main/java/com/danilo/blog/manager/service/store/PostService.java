package com.danilo.blog.manager.service.store;

import com.danilo.blog.manager.models.Post;
import com.danilo.blog.manager.repository.store.IPostRepository;
import com.danilo.blog.manager.repository.store.IUserRepository;
import com.danilo.blog.manager.security.AppUsernamePasswordAuthenticationToken;
import com.danilo.blog.manager.utils.Sorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
public class PostService extends CrudService<Post, Long> {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    public PostService(IPostRepository repository){
        super(repository);
    }

    public List<Post> list(int page, int perPage, Sorter sorter, String term, Long blogId, Long categoryId){
        return ((IPostRepository) repository).findAllByParameters(term, blogId, categoryId, PageRequest.of(page, perPage));
    }

    @Override
    public Post create(Post post){
        AppUsernamePasswordAuthenticationToken authentication = (AppUsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        post.setAuthor(userRepository.getReferenceById(authentication.getId()));
        return super.create(post);
    }

}
