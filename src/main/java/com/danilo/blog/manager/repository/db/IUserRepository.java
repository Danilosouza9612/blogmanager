package com.danilo.blog.manager.repository.db;

import com.danilo.blog.manager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.username = ?1 or u.email = ?1")
    public Optional<User> findByUsername(String username);
}
