package com.danilo.blog.manager.configuration.local;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.danilo.blog.manager.configuration.IS3Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("local")
@Configuration
public class S3Config implements IS3Config {

    @Override
    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                "https://localhost.localstack.cloud:4566",
                                Regions.US_EAST_1.getName()
                        )
                )
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .enablePathStyleAccess()
                .build();
    }
}
