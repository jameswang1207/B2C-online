package com.search2.rpc.imp;

import com.b2c.rpc.annotation.RpcService;
import com.b2c.rpc.common.HelloService;

// 指定远程接口
@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService{
    @Override
    public String hello(String name) {
        return "hello " + name;
    }
}
