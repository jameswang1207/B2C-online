package com.search2.protal.solrcloud;

import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrCloudTest {
    @Test
    public void inputDocument() throws Exception{
        //创建一个和solr集群的连接
        //参数就是zookeeper的地址列表，使用逗号分隔
        String zkHost = "192.168.222.118:2181,192.168.222.118:2182,192.168.222.118:2183";
        CloudSolrClient solrServer = new CloudSolrClient(zkHost);
        //设置默认的collection
        solrServer.setDefaultCollection("new_core1");
        //创建一个文档对象
        SolrInputDocument document = new SolrInputDocument();
        //向文档中添加域
        document.addField("id", "test304");
        document.addField("item_title", "大金空调官网");
        //把文档添加到索引库
        solrServer.add(document);
        //提交
        solrServer.commit();
    }
}
