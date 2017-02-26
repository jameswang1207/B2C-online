package com.b2c.service.imp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.b2c.mapper.TbItemParamMapper;
import com.b2c.pojo.TbItemParam;
import com.b2c.pojo.TbItemParamExample;
import com.b2c.pojo.TbItemParamExample.Criteria;
import com.b2c.service.ItemParamService;

@Service
public class ItemParamServiceImp implements ItemParamService{

    @Autowired
    private TbItemParamMapper itemParamMapper;
    
    @Override
    public String getItemParamByCid(long cid) {
        TbItemParamExample example = new TbItemParamExample();
        Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(cid);
        List<TbItemParam> list = itemParamMapper.selectByExample(example);
        if(list != null && list.size() > 0){
            return "{\"status\": 200, \"data\":" + JSON.toJSONString(list) +"}";
        } else {
            return "{\"status\": 500, \"message\": \"no data\"}";
        }
    }

    @Override
    public String insertItemParam(TbItemParam itemParam) {
        //补全pojo
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        //插入到规格参数模板表
        itemParamMapper.insert(itemParam);
        return "{\"satus\":\"200\"}";
    }

}
