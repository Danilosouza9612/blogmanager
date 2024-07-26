package com.danilo.autoparts.manager.service.store;

import com.danilo.autoparts.manager.models.Page;
import com.danilo.autoparts.manager.repository.store.IPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageService extends CrudService<Page, Long>{
    @Autowired
    public PageService(IPageRepository pageRepository){
        super(pageRepository);
    }
}
