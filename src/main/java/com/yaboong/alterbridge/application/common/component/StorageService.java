package com.yaboong.alterbridge.application.common.component;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by yaboong on 2019-09-11
 */
public interface StorageService {

    String upload(MultipartFile multipartFile) throws IOException;

    void delete(String fileIdentifier);

}
