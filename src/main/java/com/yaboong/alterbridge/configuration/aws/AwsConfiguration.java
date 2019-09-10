package com.yaboong.alterbridge.configuration.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yaboong on 2019-09-11
 */
@Configuration
public class AwsConfiguration {

    @Value("${aws.credentials.access-key}")
    private String AWS_ACCESS_KEY;

    @Value("${aws.credentials.secret-key}")
    private String AWS_SECRET_KEY;

    @Value("${aws.s3.region}")
    private String AWS_S3_REGION;

    @Bean
    public AmazonS3 getS3Client() {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
        AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(basicAWSCredentials);
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(AWS_S3_REGION)
                .withCredentials(credentialsProvider)
                .build();
    }
}
