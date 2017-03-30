package com.search2.rpc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.b2c.rpc.util.Constant;
import com.b2c.rpc.zk.ZkOption;

@Component
public class ServiceRegistry {
    @Value("${registry.address}")
    private String registryAddress;

    public void register(String data) {
        ZkOption.create(Constant.ZK_DATA_PATH, data.getBytes(), registryAddress);
    }
}
