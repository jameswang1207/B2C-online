package com.b2c.rest.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b2c.mapper.TbContentMapper;
import com.b2c.pojo.TbContent;
import com.b2c.pojo.TbContentExample;
import com.b2c.pojo.TbContentExample.Criteria;
import com.b2c.rest.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {
    
    @Autowired
    private TbContentMapper contentMapper;
    @Override
    public List<TbContent> getContentList(long contentCid) {
        //根据内容分类id查询内容列表
        TbContentExample example = new TbContentExample();
        Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(contentCid);
        //执行查询
        List<TbContent> list = contentMapper.selectByExample(example);
        return list;
    }
}