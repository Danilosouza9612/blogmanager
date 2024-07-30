package com.danilo.blog.manager.service.store;

import com.danilo.blog.manager.models.Category;
import com.danilo.blog.manager.repository.store.ICategoryRepository;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends CrudService<Category, Long> {
    @Autowired
    public CategoryService(ICategoryRepository repository) {
        super(repository);
    }
}
