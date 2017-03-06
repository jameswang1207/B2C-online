package com.solr.test;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrTest {
    @Test
    public void insertDoc() throws Exception{
        SolrServer server = new HttpSolrServer("192.168.222.115:8080/solr/");
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", "test001");
        document.addField("name", "test商品");
        server.add(document);
        server.commit();
    }
    
    @Test
    public void dele() throws Exception{
        SolrServer server = new HttpSolrServer("192.168.222.115:8080/solr/");
        server.deleteByQuery("*:");
        server.commit();
    }
}
