package com.b2c.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b2c.pojo.TbItemParam;
import com.b2c.service.ItemParamService;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {

    @Autowired
    private ItemParamService itemParamService;
    
    @RequestMapping("/query/itemcatid/{itemCatId}")
    @ResponseBody
    public String getItemParamByCid(@PathVariable Long itemCatId) {
        return  itemParamService.getItemParamByCid(itemCatId);
    }
    
    @RequestMapping("/save/{cid}")
    @ResponseBody
    public String insertItemParam(@PathVariable Long cid, String paramData) {
        //创建pojo对象
        TbItemParam itemParam = new TbItemParam();
        itemParam.setItemCatId(cid);
        itemParam.setParamData(paramData);
        return itemParamService.insertItemParam(itemParam);
    }
}