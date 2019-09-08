package com.yaboong.alterbridge.application.common.component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by yaboong on 2019-09-09
 */
@Component
public class AwsS3Client {

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

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFile(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(AWS_S3_BUCKET_NAME, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String uploadFile(MultipartFile multipartFile) {

        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            fileUrl = AWS_S3_END_POINT_URL + "/" + AWS_S3_BUCKET_NAME + "/" + fileName;
            this.uploadFile(fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(new DeleteObjectRequest(AWS_S3_BUCKET_NAME + "/", fileName));
        return "Successfully deleted";
    }

}
