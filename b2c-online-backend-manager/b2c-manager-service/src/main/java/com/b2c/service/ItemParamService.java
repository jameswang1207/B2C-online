package com.b2c.service;

import com.b2c.pojo.TbItemParam;

public interface ItemParamService {
    public String getItemParamByCid(long cid);
    public String insertItemParam(TbItemParam itemParam);
}
