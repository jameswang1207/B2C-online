package com.search2.rpc.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b2c.rpc.pojo.RpcRequest;
import com.b2c.rpc.pojo.RpcResponse;

@Component
public class RpcProxy {
    private String serverAddress;
    @Autowired
    private ServiceDiscovery serviceDiscovery;
    
    @SuppressWarnings("unchecked")
    public <T> T create(Class<?> interfaceClass) {
      return (T) Proxy.newProxyInstance(
        interfaceClass.getClassLoader(),new Class<?>[]{interfaceClass},
        new InvocationHandler() {
          public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            RpcRequest request = new RpcRequest(); // 创建并初始化 RPC 请求
            request.setRequestId(UUID.randomUUID().toString());
            request.setClassName(method.getDeclaringClass().getName());
            request.setMethodName(method.getName());
            request.setParameterTypes(method.getParameterTypes());
            request.setParameters(args);
   
            if (serviceDiscovery != null) {
              serverAddress = serviceDiscovery.discover(); //发现服务
            }
   
            String[] array = serverAddress.split(":");
            String host = array[0];
            int port = Integer.parseInt(array[1]);
   
            RpcClient client = new RpcClient(host, port); //初始化 RPC 客户端
            RpcResponse response = client.send(request); //通过 RPC 客户端发送 RPC 请求并获取 RPC 响应
   
            if (client.isSharable()) {
              throw response.getError();
            } else {
              return response.getResult();
            }
          }
        }
      );
    }
}
