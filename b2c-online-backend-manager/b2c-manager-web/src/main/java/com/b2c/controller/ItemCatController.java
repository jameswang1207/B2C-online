package com.b2c.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b2c.commom.EUTreeNode;
import com.b2c.service.ItemCatService;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
    
    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/list")
    @ResponseBody
    //如果id为null是使用默认值，也就是parentid为0的分类列表
    public List<EUTreeNode> categoryList(@RequestParam(value="id", defaultValue="0") Long parentId) throws Exception {
        //查询数据库
        List<EUTreeNode> list = itemCatService.getCatList(parentId);
        if (list != null && list.size() > 0){
            return list;
        }
        return null;
    }
    
}