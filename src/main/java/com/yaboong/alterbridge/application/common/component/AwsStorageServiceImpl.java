package com.yaboong.alterbridge.application.common.component;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * Created by yaboong on 2019-09-09
 */
@Service
@RequiredArgsConstructor
public class AwsStorageServiceImpl implements StorageService {

    @Value("${aws.s3.end-point-url}")
    private String AWS_S3_END_POINT_URL;

    @Value("${aws.s3.bucket-name}")
    private String AWS_S3_BUCKET_NAME;

    private final AmazonS3 s3client;

    @Override
    public String upload(MultipartFile multipartFile) throws IOException {
        Objects.requireNonNullElseGet(multipartFile, () -> new IllegalArgumentException("multipartFile is null"));
        multipartFile.getOriginalFilename();

        String fileName = generateFileName(multipartFile.getOriginalFilename());
        File file = convertMultiPartToFile(multipartFile, fileName);
        uploadFile(fileName, file);

        file.delete();
        return generateFileUrl(fileName);
    }

    @Override
    public void delete(String fileIdentifier) {
        String fileName = String.format("/%s", getFileNameFromUrl(fileIdentifier));
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
