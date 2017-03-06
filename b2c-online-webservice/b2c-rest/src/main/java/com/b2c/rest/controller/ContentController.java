package com.b2c.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b2c.pojo.B2cResult;
import com.b2c.pojo.TbContent;
import com.b2c.rest.service.ContentService;
import com.b2c.util.ExceptionUtil;

@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;
    
    @RequestMapping("/list/{contentCategoryId}")
    @ResponseBody
    public B2cResult getContentList(@PathVariable Long contentCategoryId) {
        try {
            List<TbContent> list = contentService.getContentList(contentCategoryId);
            return B2cResult.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return B2cResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}