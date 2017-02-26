package com.b2c.service.imp;

import java.io.IOException;
import java.io.InputStream;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.b2c.pojo.PictureResult;
import com.b2c.service.PictureService;
import com.b2c.util.FtpUtil;
import com.b2c.util.IDUtils;

/**
 * image upload
 * @author james
 *
 */
@Service
public class PictureServiceImp implements PictureService{

    @Value("${FTP_ADDRESS}")
    private String FTP_ADDRESS;
    @Value("${FTP_PORT}")
    private int FTP_PORT;
    @Value("${FTP_USERNAME}")
    private String FTP_USERNAME;
    @Value("${FTP_PASSWORD}")
    private String FTP_PASSWORD;
    @Value("${FTP_BASE_PATH}")
    private String FTP_BASE_PATH;
    @Value("${IMAGE_BASE_URL}")
    private String IMAGE_BASE_URL;

    @Override
    public String uploadFile(MultipartFile uploadFile) {
        return JSON.toJSONString(saveFile(uploadFile));
    }
    
    private PictureResult saveFile(MultipartFile uploadFile) {
        if (uploadFile.isEmpty()) {
            return null;
        }
        String oldName = uploadFile.getOriginalFilename();
        //new image name
        String newName = IDUtils.genImageName() + oldName.substring(oldName.lastIndexOf('.'), oldName.length());
        //use jodo new date
        String filePath = new DateTime().toString("yyyy/MM/dd");
        
        InputStream input = null;
        PictureResult result = new PictureResult();
        try {
            input = uploadFile.getInputStream();
            boolean isSuccess = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASE_PATH, filePath, newName, input);
            result.setCode(isSuccess ? 200 : 500);
            String url = IMAGE_BASE_URL + filePath + "/" + newName;
            result.setUrl(url);
        } catch (IOException e) {
            result.setCode(500);
            result.setUrl("image upload is error!");
            e.printStackTrace();
        }
        return result;
    }
}
