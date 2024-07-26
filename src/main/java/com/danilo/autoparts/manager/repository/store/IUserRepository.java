package com.danilo.autoparts.manager.repository.store;

import com.danilo.autoparts.manager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByUsername(String username);
}
