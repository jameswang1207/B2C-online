package com.search.service.imp;

import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b2c.pojo.B2cResult;
import com.search.mapper.ItemMapper;
import com.search.pojo.Item;
import com.search.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService{

    @Autowired
    private ItemMapper itemMapper;
    
    @Autowired
    private SolrClient solrServer;
    
    @Override
    public B2cResult importAllItems() {
        
        try{
            List<Item> items = itemMapper.getItemList();
            for(Item item:items){
                //创建一个SolrInputDocument对象
                SolrInputDocument document = new SolrInputDocument();
                document.setField("id", item.getId());
                document.setField("item_title", item.getTitle());
                document.setField("item_sell_point", item.getSellPoint());
                document.setField("item_price", item.getPrice());
                document.setField("item_image", item.getImage());
                document.setField("item_category_name", item.getCategoryName());
                document.setField("item_desc", item.getItemDesc());
                //写入索引库
                solrServer.add(document);
                solrServer.commit();
            }
        }catch(Exception exception){
            exception.printStackTrace();
            return B2cResult.build(500, exception.getMessage());
        }
        return B2cResult.ok();
    }

}
