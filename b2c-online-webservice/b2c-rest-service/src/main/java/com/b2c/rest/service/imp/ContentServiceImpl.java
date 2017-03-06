package com.b2c.rest.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.b2c.mapper.TbContentMapper;
import com.b2c.pojo.TbContent;
import com.b2c.pojo.TbContentExample;
import com.b2c.pojo.TbContentExample.Criteria;
import com.b2c.rest.jedis.JedisClient;
import com.b2c.rest.service.ContentService;
import com.b2c.util.JsonUtils;

@Service
public class ContentServiceImpl implements ContentService {
    
    @Autowired
    private TbContentMapper contentMapper;
    
    @Autowired
    private JedisClient jedisClient;
    
    @Value("${INDEX_CONTENT_CACHE_KEY}")
    private String INDEX_CONTENT_CACHE_KEY;
    @Override
    public List<TbContent> getContentList(long contentCid) {
        
        //从緩存中取
         try{
            String cacheStr = jedisClient.hget(INDEX_CONTENT_CACHE_KEY, contentCid + "");
            if(!StringUtils.isEmpty(cacheStr)){
                List<TbContent> list = JsonUtils.jsonToList(cacheStr, TbContent.class);
                return list;
            }
         }catch(Exception exception){
             exception.printStackTrace();
         }
        
        //根据内容分类id查询内容列表
        TbContentExample example = new TbContentExample();
        Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(contentCid);
        //执行查询
        List<TbContent> list = contentMapper.selectByExample(example);
        
        //添加到缓存中
         try{
             jedisClient.hset(INDEX_CONTENT_CACHE_KEY, contentCid + "", JSON.toJSONString(list));
         }catch(Exception exception){
             exception.printStackTrace();
         }

        return list;
    }
}