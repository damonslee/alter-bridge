package com.yaboong.alterbridge.application.common.component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * Created by yaboong on 2019-09-09
 */
@Component
public class S3Client {

    private AmazonS3 s3client;

    @Value("${aws.credentials.access-key}")
    private String AWS_ACCESS_KEY;

    @Value("${aws.credentials.secret-key}")
    private String AWS_SECRET_KEY;

    @Value("${aws.s3.region}")
    private String AWS_S3_REGION;

    @Value("${aws.s3.end-point-url}")
    private String AWS_S3_END_POINT_URL;

    @Value("${aws.s3.bucket-name}")
    private String AWS_S3_BUCKET_NAME;

    @PostConstruct
    private void initializeAmazon() {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
        AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(basicAWSCredentials);
        this.s3client = AmazonS3ClientBuilder
                .standard()
                .withRegion(AWS_S3_REGION)
                .withCredentials(credentialsProvider)
                .build();
    }

    public String upload(MultipartFile multipartFile) throws IOException {
        Objects.requireNonNullElseGet(multipartFile, () -> new IllegalArgumentException("multipartFile is null"));
        multipartFile.getOriginalFilename();

        String fileName = generateFileName(multipartFile.getOriginalFilename());
        File file = convertMultiPartToFile(multipartFile, fileName);
        uploadFile(fileName, file);

        file.delete();
        return generateFileUrl(fileName);
    }

    public void delete(String fileUrl) {
        String fileName = String.format("/%s", getFileNameFromUrl(fileUrl));
        s3client.deleteObject(new DeleteObjectRequest(AWS_S3_BUCKET_NAME, fileName));
    }

    private String getFileNameFromUrl(String fileUrl) {
        return fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
    }

    private String generateFileName(String originalFileName) {
        return String.format("%s_%s", RandomStringUtils.randomAlphanumeric(10), originalFileName);
    }

    private File convertMultiPartToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File file = new File(fileName);
        try (FileOutputStream os = new FileOutputStream(file)) {
            os.write(multipartFile.getBytes());
        }
        return file;
    }

    private String generateFileUrl(String fileName) {
        return String.format("%s/%s/%s", AWS_S3_END_POINT_URL, AWS_S3_BUCKET_NAME, fileName);
    }

    private PutObjectResult uploadFile(String fileName, File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(AWS_S3_BUCKET_NAME, fileName, file);
        PutObjectRequest putObjectRequestWithAcl = putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
        return s3client.putObject(putObjectRequestWithAcl);
    }

}
