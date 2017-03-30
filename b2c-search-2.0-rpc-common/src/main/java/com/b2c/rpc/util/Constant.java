package com.b2c.rpc.util;

public interface Constant {
    
    //在创建数据节点前，先用zkCli.sh客户端连接上服务端，查看目前存在的数据节点，
    //把下面的/rpc-service-register改为你自己的
    //     
    int ZK_SESSION_TIMEOUT = 5000;
    
    String ZK_REGISTRY_PATH = "/rpc-service-register";
    String ZK_DATA_PATH = ZK_REGISTRY_PATH + "/data";
}