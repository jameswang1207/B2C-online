package com.solr.test;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrTest {
    @Test
    public void insertDoc() throws Exception{
        String zkHost = "192.168.222.118:2181,192.168.222.118:2182,192.168.222.118:2183";
        org.apache.solr.client.solrj.impl.HttpSolrClient.Builder build = new HttpSolrClient.Builder(zkHost);
        SolrClient server = build.build();
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", "test001");
        document.addField("item_title", "testddd商品");
        server.add(document);
        server.commit();
    }
    
    @Test
    public void volatileTest(){
        
    }
}
