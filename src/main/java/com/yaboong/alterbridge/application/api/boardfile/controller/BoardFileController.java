package com.yaboong.alterbridge.application.api.boardfile.controller;

import com.yaboong.alterbridge.application.api.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by yaboong on 2019-09-09
 */
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class BoardFileController {

    private final StorageService storageService;

    @PostMapping
    public ResponseEntity upload(@RequestPart(value = "file") MultipartFile file) throws IOException {
        String fileUrl = storageService.upload(file);
        return ResponseEntity
                .ok()
                .body(fileUrl);
    }

    @DeleteMapping
    public ResponseEntity deleteFile(@RequestPart(value = "url") String fileUrl) {
        storageService.delete(fileUrl);
        return ResponseEntity
                .ok()
                .build();
    }

}
