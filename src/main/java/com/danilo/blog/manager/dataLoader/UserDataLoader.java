package com.danilo.blog.manager.dataLoader;

import com.danilo.blog.manager.models.User;
import com.danilo.blog.manager.models.UserRole;
import com.danilo.blog.manager.repository.store.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDataLoader implements CommandLineRunner {
    private static Logger LOGGER = LoggerFactory.getLogger(UserDataLoader.class);
    @Autowired
    private IUserRepository repository;
    @Autowired
    private PasswordEncoder encoder;
    @Value("{app.root.password")
    private String rootPassword;

    @Override
    public void run(String... args) throws Exception {
        User user;
        if(repository.findByUsername("root").isEmpty()){
            user = new User();
            LOGGER.info("CREATING ROOT USER");
            user.setRole(UserRole.ROOT);
            user.setEmail("root@app.com");
            user.setPassword(encoder.encode("test@123"));
            user.setName("Root user");
            user.setUsername("root");
            repository.save(user);
            LOGGER.info("Root user created");
        }
    }
}
