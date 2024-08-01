package com.danilo.blog.manager.service;

import com.danilo.blog.manager.models.Tag;
import com.danilo.blog.manager.repository.db.ITagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService extends CrudService<Tag, Long>{

    @Autowired
    public TagService(ITagRepository repository){
        super(repository);
    }
}
