package com.danilo.blog.manager.repository.file;

import com.danilo.blog.manager.models.Blog;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface IStorageRepository {
    public String upload(Blog blog, MultipartFile file) throws IOException;
    List<File> findAll(Blog blog);
    void delete(Blog blog, String identifier);
}
