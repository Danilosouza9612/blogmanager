package com.danilo.blog.manager.service;

import com.danilo.blog.manager.models.Category;
import com.danilo.blog.manager.repository.db.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends CrudService<Category, Long> {
    @Autowired
    public CategoryService(ICategoryRepository repository) {
        super(repository);
    }
}
