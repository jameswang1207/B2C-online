package com.b2c.portal.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.b2c.pojo.B2cResult;
import com.b2c.pojo.TbContent;
import com.b2c.portal.service.ContentService;
import com.b2c.util.HttpClientUtil;
import com.b2c.util.JsonUtils;

@Service
public class ContentServiceImpl implements ContentService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_INDEX_AD_URL}")
    private String REST_INDEX_AD_URL;
    
    @Override
    public String getContentList() {
        //调用服务层的服务
        String result = HttpClientUtil.doGet(REST_BASE_URL + REST_INDEX_AD_URL);
        //把字符串转换成B2cResult
        try {
            B2cResult B2cResult = com.b2c.pojo.B2cResult.formatToList(result, TbContent.class);
            //取内容列表
            @SuppressWarnings("unchecked")
            List<TbContent> list = (List<TbContent>) B2cResult.getData();
            List<Map<String,Object>> resultList = new ArrayList<>();
            //创建一个jsp页码要求的pojo列表
            for (TbContent tbContent : list) {
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("src", tbContent.getPic());
                map.put("height", 240);
                map.put("width", 670);
                map.put("srcB", tbContent.getPic2());
                map.put("widthB", 550);
                map.put("heightB", 240);
                map.put("href", tbContent.getUrl());
                map.put("alt", tbContent.getSubTitle());
                resultList.add(map);
            }
            return JsonUtils.objectToJson(resultList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}