package com.b2c.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b2c.commom.EUTreeNode;
import com.b2c.mapper.TbItemCatMapper;
import com.b2c.pojo.TbItemCat;
import com.b2c.pojo.TbItemCatExample;
import com.b2c.pojo.TbItemCatExample.Criteria;
import com.b2c.service.ItemCatService;

@Service
public class ItemCatServiceImp implements ItemCatService{

    @Autowired
    TbItemCatMapper itemCatMapper;
    
    @Override
    public List<EUTreeNode> getCatList(long parentId) {
        TbItemCatExample example = new TbItemCatExample();
        Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> itemcats = itemCatMapper.selectByExample(example);
        List<EUTreeNode> treeNodeList = new ArrayList<EUTreeNode>();
        if(itemcats != null && itemcats.size() > 0){
            for (TbItemCat item : itemcats){
                EUTreeNode treeNode = new EUTreeNode();
                treeNode.setId(item.getId());
                treeNode.setText(item.getName());
                treeNode.setState(item.getIsParent() ? "close" : "open");
                treeNodeList.add(treeNode);
            }
        }
        return treeNodeList;
    }

}
