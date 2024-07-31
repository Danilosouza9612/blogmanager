package com.danilo.blog.manager.configuration.prod;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.danilo.blog.manager.configuration.IS3Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!local")
public class S3Config implements IS3Config {

    @Value("{aws.region}")
    private String region;

    @Override
    @Bean
    public AmazonS3 amazonS3(){
         return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();
    }
}
