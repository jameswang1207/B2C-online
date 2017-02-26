package com.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FtpTest {
    public void test() throws SocketException, IOException{
        //创建对象
        //创建连接
        //设置密码
        //上传文件
        FTPClient client = new FTPClient();
        client.connect("hostname", 21);
        client.login("jameswang", "abc123_");
        
        FileInputStream inputStream = new FileInputStream(new File(""));
        client.setFileType(FTP.BINARY_FILE_TYPE);
        
        client.changeWorkingDirectory("/home/james/Desktop/image");
        client.storeFile("login.png", inputStream);
        inputStream.close();
        
    }
}
