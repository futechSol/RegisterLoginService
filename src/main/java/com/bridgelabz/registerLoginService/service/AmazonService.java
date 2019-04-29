package com.bridgelabz.registerLoginService.service;

import org.springframework.web.multipart.MultipartFile;
import com.bridgelabz.registerLoginService.util.Response;

public interface AmazonService {
    Response uploadFile(MultipartFile multipartFile, Long userId);
    String getProfilePicFromS3Bucket(Long userId);
}
