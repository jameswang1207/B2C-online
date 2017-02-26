package com.b2c.service;

import com.b2c.exception.SqlInsertException;
import com.b2c.pojo.EUDataGridResult;
import com.b2c.pojo.TbItem;

public interface ItemService {
    public TbItem getItemById(long id);
    
    public EUDataGridResult getItemList(int page, int rows);
    
    public String createItem(TbItem item, String desc) throws SqlInsertException;

}
