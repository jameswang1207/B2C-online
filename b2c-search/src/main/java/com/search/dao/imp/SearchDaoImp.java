package com.search.dao.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.search.dao.SearchDao;
import com.search.pojo.Item;
import com.search.pojo.SearchResult;

@Repository
public class SearchDaoImp implements SearchDao{

    @Autowired
    private SolrServer solrServer;
    
    @Override
    public SearchResult search(SolrQuery query) throws Exception {
        
        SearchResult searchResult = new SearchResult();
        //根据查询条件查询索引库
        QueryResponse response = solrServer.query(query);
        //取查询结果
        SolrDocumentList lists = response.getResults();
        //取查询结果总数量
        searchResult.setRecordCount(lists.getNumFound());
        //取高亮显示
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        //商品列表
        List<Item> itemList = new ArrayList<Item>();
        
        for(SolrDocument doc:lists){
            //创建一商品对象
            Item item = new Item();
            item.setId((String)doc.get("id"));
            String title = "";
            List<String> list = highlighting.get(doc.get("id")).get("item_title");
            if (list != null && list.size()>0) {
                title = list.get(0);
            } else {
                title = (String) doc.get("item_title");
            }
            item.setTitle(title);
            
            item.setImage((String) doc.get("item_image"));
            item.setPrice((long) doc.get("item_price"));
            item.setSellPoint((String) doc.get("item_sell_point"));
            item.setCategoryName((String) doc.get("item_category_name"));
            itemList.add(item);
        }
        searchResult.setItemList(itemList);
        
        return searchResult;
    }
}
