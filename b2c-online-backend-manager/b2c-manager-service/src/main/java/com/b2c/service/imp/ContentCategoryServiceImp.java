package com.b2c.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b2c.mapper.TbContentCategoryMapper;
import com.b2c.pojo.EUTreeNode;
import com.b2c.pojo.TbContentCategory;
import com.b2c.pojo.TbContentCategoryExample;
import com.b2c.pojo.TbContentCategoryExample.Criteria;
import com.b2c.service.ContentCategoryService;

@Service
public class ContentCategoryServiceImp implements ContentCategoryService{

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;
    @Override
    public List<EUTreeNode> getCategoryList(long parentId) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
        List<EUTreeNode> results = null;
        if(list != null && list.size() > 0){
            results = new ArrayList<EUTreeNode>();
            for(TbContentCategory content : list){
                EUTreeNode node = new EUTreeNode();
                node.setId(content.getId());
                node.setText(content.getName());
                node.setState(content.getIsParent() ? "closed" : "open");
                results.add(node);
            }
        }
        return results;
    }
}
