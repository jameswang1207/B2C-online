package com.b2c.service;

import java.util.List;

import com.b2c.commom.EUTreeNode;

public interface ItemCatService {
    public List<EUTreeNode> getCatList(long parentId);
}
