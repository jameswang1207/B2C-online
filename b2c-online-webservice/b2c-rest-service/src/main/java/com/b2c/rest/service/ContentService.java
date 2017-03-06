package com.b2c.rest.service;

import java.util.List;

import com.b2c.pojo.TbContent;

public interface ContentService {
    public List<TbContent> getContentList(long contentCid);
}
