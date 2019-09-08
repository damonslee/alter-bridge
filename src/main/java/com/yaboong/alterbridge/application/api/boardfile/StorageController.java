package com.yaboong.alterbridge.application.api.boardfile;

import com.yaboong.alterbridge.application.common.component.AwsS3Client;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by yaboong on 2019-09-09
 */
@RestController
@RequestMapping("/storage")
@RequiredArgsConstructor
public class StorageController {

    private final AwsS3Client awsS3Client;

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return awsS3Client.uploadFile(file);
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return awsS3Client.deleteFileFromS3Bucket(fileUrl);
    }

}
