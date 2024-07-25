package com.danilo.autoparts.manager.service.store;

import org.springframework.data.repository.CrudRepository;

import javax.management.InstanceNotFoundException;
import java.util.Optional;

public abstract class CrudService<T, ID>{
    protected final CrudRepository<T, ID> repository;
    public CrudService(CrudRepository<T, ID> repository){
        this.repository = repository;
    }

    public Iterable<T> list(){
        return this.repository.findAll();
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
