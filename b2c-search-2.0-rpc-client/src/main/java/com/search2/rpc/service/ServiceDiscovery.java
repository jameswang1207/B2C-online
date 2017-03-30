package com.search2.rpc.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.b2c.rpc.exception.ZkConnectionException;
import com.b2c.rpc.zk.ZkOption;

@Component
public class ServiceDiscovery {

    @Value("${registry.address}")
    private String registryAddress;
    
    public String discover() throws Exception {
        return ZkOption.discover(registryAddress);
    }
}
