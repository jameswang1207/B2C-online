package com.b2c.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b2c.rest.pojo.CatResult;
import com.b2c.rest.service.ItemCatService;
import com.b2c.util.JsonUtils;

@Controller
@RequestMapping("/itemcat")
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    /**
     * 3.0--4.1
     * @param callback
     * @return
     */
    @RequestMapping(value="/list3", produces=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public String getItemCatList3x(String callback) {
        CatResult catResult = itemCatService.getItemCatList();
        //把pojo转换成字符串
        String json = JsonUtils.objectToJson(catResult);
        //拼装返回值
        String result = callback + "(" + json + ");";
        return result;
    }
    
    @RequestMapping(value="/list", produces=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public Object getItemCatList41x(String callback) {
        CatResult catResult = itemCatService.getItemCatList();
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(catResult);
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
    }

}
