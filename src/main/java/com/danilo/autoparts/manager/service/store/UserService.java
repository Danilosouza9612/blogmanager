package com.danilo.autoparts.manager.service.store;

import com.danilo.autoparts.manager.exception.BusinessRuleViolationException;
import com.danilo.autoparts.manager.exception.ErrorSerialization;
import com.danilo.autoparts.manager.models.User;
import com.danilo.autoparts.manager.repository.store.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InstanceNotFoundException;

@Service
public class UserService extends CrudService<User, Long> {
    public UserService(@Autowired IUserRepository repository){
        super(repository);
    }

    @Override
    public User create(User user){
        this.throwUniqueBusinessRuleViolationExceptionIfUsernameIsNotUnique(user);
        return super.create(user);
    }

    @Override
    public User update(Long id, User user) throws InstanceNotFoundException {
        this.throwUniqueBusinessRuleViolationExceptionIfUsernameIsNotUnique(user);
        return super.update(id, user);
    }

    private boolean usernameExists(String username){
        IUserRepository userRepository = (IUserRepository) this.repository;
        return userRepository.findByUsername(username).isPresent();
    }

    private void throwUniqueBusinessRuleViolationExceptionIfUsernameIsNotUnique(User user){
        if(this.usernameExists(user.getUsername())){
            ErrorSerialization errorSerialization = new ErrorSerialization();
            errorSerialization.addError("username", "Já existe um usuário com esse nome");
            throw new BusinessRuleViolationException(errorSerialization);
        }
    }
}
