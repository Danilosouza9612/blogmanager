package com.danilo.blog.manager.configuration;

import com.danilo.blog.manager.repository.file.IStorageRepository;
import com.danilo.blog.manager.repository.file.S3StorageRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public IStorageRepository fileRepository(){
        return new S3StorageRepository("blog-files");
    }
}
