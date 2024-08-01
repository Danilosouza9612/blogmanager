package com.danilo.blog.manager.service;

import com.danilo.blog.manager.models.Page;
import com.danilo.blog.manager.repository.db.IPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageService extends CrudService<Page, Long>{
    @Autowired
    public PageService(IPageRepository pageRepository){
        super(pageRepository);
    }
}
