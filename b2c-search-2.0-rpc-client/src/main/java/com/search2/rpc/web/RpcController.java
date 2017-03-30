package com.search2.rpc.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.b2c.rpc.common.HelloService;
import com.search2.rpc.service.RpcProxy;

@RestController 
@RequestMapping(value="/rpc")
public class RpcController {
    
    @Autowired
    private RpcProxy rpcProxy;
    
    @RequestMapping(value="/hello", method=RequestMethod.GET)
    public String getUserList() { 
        HelloService helloService = rpcProxy.create(HelloService.class);
        String result = helloService.hello("james");
        return result; 
    }
}