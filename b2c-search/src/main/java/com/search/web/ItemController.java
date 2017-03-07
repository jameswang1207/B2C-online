package com.search.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b2c.pojo.B2cResult;
import com.search.service.ItemService;

@Controller
@RequestMapping("/manager")
public class ItemController {
    
    @Autowired
    private ItemService itemService;

    /**
     * 导入商品数据到索引库
     */
    @RequestMapping("/importall")
    @ResponseBody
    public B2cResult importAllItems() {
        return  itemService.importAllItems();
    }
}
