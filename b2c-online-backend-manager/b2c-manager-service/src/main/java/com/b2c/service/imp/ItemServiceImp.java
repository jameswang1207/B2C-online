package com.b2c.service.imp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b2c.exception.SqlInsertException;
import com.b2c.mapper.TbItemDescMapper;
import com.b2c.mapper.TbItemMapper;
import com.b2c.pojo.EUDataGridResult;
import com.b2c.pojo.TbItem;
import com.b2c.pojo.TbItemDesc;
import com.b2c.pojo.TbItemExample;
import com.b2c.pojo.TbItemExample.Criteria;
import com.b2c.service.ItemService;
import com.b2c.util.IDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ItemServiceImp implements ItemService{

    @Autowired
    private TbItemMapper itemMapper;
    
    @Autowired
    private TbItemDescMapper ItemDescMapper;

    @Override
    public TbItem getItemById(long id) {
        //添加查询条件
        TbItemExample example = new TbItemExample();
        Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        
        List<TbItem> item = itemMapper.selectByExample(example);
        if(item != null && item.size() > 0){
            return item.get(0);
        }
        return null;
    }

    @Override
    public EUDataGridResult getItemList(int page, int rows) {
        //查询商品列表
        TbItemExample example = new TbItemExample();
        //分页处理
        PageHelper.startPage(page, rows);
        List<TbItem> list = itemMapper.selectByExample(example);
        //创建一个返回值对象
        EUDataGridResult result = new EUDataGridResult();
        result.setRows(list);
        //取记录总条数
        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public String createItem(TbItem item, String desc) throws SqlInsertException {
        long itemId = IDUtils.genItemId();
        item.setId(itemId);
        // status three status : 1:正常,2:下架,3:删除
        item.setStatus((byte)1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        
        itemMapper.insert(item);
        int isSuccess = createItemDesc(itemId, desc);
        if (isSuccess != 1) {
            throw new SqlInsertException("insert item desc has exception", 1000);
        }
        return "{\"code\":200,\"message\":\"ok\"}";
    }
    
    private int createItemDesc(long itemId, String desc){
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        itemDesc.setItemDesc(desc);
        return ItemDescMapper.insert(itemDesc);
    }
    

}
