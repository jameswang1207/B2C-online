package com.search.solr;

import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient.Builder;

public class SolrServerconfig {
    public static CloudSolrClient newInstance(){
        String zkHost = "192.168.222.118:2181,192.168.222.118:2182,192.168.222.118:2183";
        Builder config = new CloudSolrClient.Builder();
        CloudSolrClient solrServer = config.withZkHost(zkHost).build();
        solrServer.setDefaultCollection("new_core1");
        return solrServer;
    }
}
