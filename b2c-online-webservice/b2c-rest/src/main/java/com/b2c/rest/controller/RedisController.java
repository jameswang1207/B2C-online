package com.b2c.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b2c.pojo.B2cResult;
import com.b2c.rest.service.JedisService;

@Controller
@RequestMapping("/cache/sync")
public class RedisController {
    
    @Autowired
    private JedisService jedisService;
    
    @RequestMapping("/content/{contentCategoryId}")
    @ResponseBody
    public B2cResult syncContent(@PathVariable Long contentCategoryId){
        return jedisService.syncContent(contentCategoryId);
    }
}
