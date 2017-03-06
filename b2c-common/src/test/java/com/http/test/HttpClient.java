package com.http.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.b2c.util.HttpClientUtil;

public class HttpClient {
    
    @Test
    public void doGet(){
        Map<String, String> param = new HashMap<>();
        String url = "http://localhost:8082/test/dos-post.api";
        param.put("name", "james");
        param.put("password", "12344");
        String result = HttpClientUtil.doPost(url, param);
        System.out.println(result);
    }
}
