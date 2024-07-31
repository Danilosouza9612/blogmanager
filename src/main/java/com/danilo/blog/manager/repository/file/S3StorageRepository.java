package com.danilo.blog.manager.repository.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.danilo.blog.manager.models.Blog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class S3StorageRepository implements IStorageRepository {
    private static final Logger logger = LoggerFactory.getLogger(S3StorageRepository.class);
    @Autowired
    private AmazonS3 amazonS3;
    private String bucketName;

    public S3StorageRepository(String bucketName){
        this.bucketName = bucketName;
    }

    @Override
    public String upload(Blog blog, MultipartFile file) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/jpeg");
        metadata.addUserMetadata("title", "someTitle");

        logger.info("UPLOAD_FILE: {} ", file.getOriginalFilename());

        PutObjectRequest putObjectRequest = new PutObjectRequest(
                this.bucketName,
                fileIdentifier(blog, file.getOriginalFilename()),
                file.getInputStream(),
                metadata
        );
        putObjectRequest.getRequestClientOptions().setReadLimit(8388608);
        logger.info("BUCKET: {}", putObjectRequest.getBucketName());
        amazonS3.putObject(putObjectRequest);
        return amazonS3.getUrl(this.bucketName, fileIdentifier(blog, file.getOriginalFilename())).toString();
    }

    @Override
    public List<File> findAll(Blog blog) {
        return List.of();
    }

    @Override
    public void delete(Blog blog, String identifier) {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(this.bucketName, this.fileIdentifier(blog, identifier));
        amazonS3.deleteObject(deleteObjectRequest);
    }

    private String fileIdentifier(Blog blog, String identifier){
        return blog.getSlug() + "/" + identifier;
    }

    private String fileIdentifier(Blog blog, File file){
        return blog.getSlug() + "/" + file.getName();
    }
}
