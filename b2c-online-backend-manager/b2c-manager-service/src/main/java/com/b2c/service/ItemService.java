package com.b2c.service;

import com.b2c.commom.EUDataGridResult;
import com.b2c.pojo.TbItem;

public interface ItemService {
    public TbItem getItemById(long id);
    
    public EUDataGridResult getItemList(int page, int rows);
}
