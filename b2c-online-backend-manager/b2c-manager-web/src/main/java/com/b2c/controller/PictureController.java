package com.b2c.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.b2c.service.PictureService;

@Controller
public class PictureController {
    
    @Autowired
    PictureService pictureService;
    
    @RequestMapping("/pic/upload")
    @ResponseBody
    public String pictureUpload(MultipartFile uploadFile){
       return pictureService.uploadFile(uploadFile);
    }
}
