package com.b2c.service;

import java.util.List;

import com.b2c.pojo.EUTreeNode;

public interface ContentCategoryService {
    public List<EUTreeNode> getCategoryList(long parentId);
}
