package com.b2c.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
