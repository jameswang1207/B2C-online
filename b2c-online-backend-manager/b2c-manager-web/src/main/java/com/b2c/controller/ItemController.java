package com.b2c.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b2c.commom.EUDataGridResult;
import com.b2c.pojo.TbItem;
import com.b2c.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
    
    @Autowired
    private ItemService itemService;
    
    @RequestMapping("/{id}")
    @ResponseBody
    public TbItem getItemById(@PathVariable long id){
        return itemService.getItemById(id);
    }
    
    @RequestMapping("/list")
    @ResponseBody
    public EUDataGridResult getItemList(Integer page, Integer rows) {
        EUDataGridResult result = itemService.getItemList(page, rows);
        return result;
    }
    
}
