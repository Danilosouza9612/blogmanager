package com.danilo.autoparts.manager.service.store;

import com.danilo.autoparts.manager.utils.Sorter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import javax.management.InstanceNotFoundException;
import java.util.Optional;

public abstract class CrudService<T, ID>{
    protected final JpaRepository<T, ID> repository;
    public CrudService(JpaRepository<T, ID> repository){
        this.repository = repository;
    }

    public Iterable<T> list(int page, int perPage, Sorter sorter){
        return this.repository.findAll(PageRequest.of(page, perPage, sorter.getSort()));
    }

    public Optional<T> show(ID id){
        return this.repository.findById(id);
    }

    public T create(T instance){
        return this.repository.save(instance);
    }

    public T update(ID id, T instance) throws InstanceNotFoundException {
        if(this.repository.existsById(id)){
            return this.repository.save(instance);
        }
        throw new InstanceNotFoundException();
    }

    public void delete(ID id){
        this.repository.deleteById(id);
    }
}
