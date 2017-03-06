package com.b2c.portal.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b2c.pojo.B2cResult;
import com.b2c.portal.service.ContentService;

@Controller
public class IndexController {

   @Autowired
   private ContentService contentService;
    
    @RequestMapping("/index")
    public String showIndex(ModelMap model) {
        String adJson = contentService.getContentList();
        System.out.println(adJson);
        model.addAttribute("ad1", adJson);
        return "index";
    }
    
    @RequestMapping(value="/test/dos-post",
                    method=RequestMethod.POST,
                    produces=MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    @ResponseBody
    public B2cResult doPost(@RequestParam("name") String name,
                            @RequestParam("password") String password){
        System.out.println("name=" + name + ",password=" + password);
        return B2cResult.ok();
    }
}
