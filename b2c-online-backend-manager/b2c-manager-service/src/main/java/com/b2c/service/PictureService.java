package com.b2c.service;

import org.springframework.web.multipart.MultipartFile;
import com.b2c.pojo.PictureResult;

public interface PictureService {
    public String uploadFile(MultipartFile uploadFile);
}
