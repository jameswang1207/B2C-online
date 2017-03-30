package com.search2.rpc.handle;

import java.util.Map;

import com.b2c.rpc.pojo.RpcRequest;
import com.b2c.rpc.pojo.RpcResponse;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

public class RpcHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private final Map<String, Object> handlerMap;
   
    public RpcHandler(Map<String, Object> handlerMap) {
      this.handlerMap = handlerMap;
    }
   
    @Override
    public void channelRead0(final ChannelHandlerContext ctx, RpcRequest request) throws Exception {
      RpcResponse response = new RpcResponse();
      response.setRequestId(request.getRequestId());
      try {
        Object result = handle(request);
        response.setResult(result);
      } catch (Throwable t) {
        response.setError(t);
      }
      ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
   
    private Object handle(RpcRequest request) throws Throwable {
      String className = request.getClassName();
      Object serviceBean = handlerMap.get(className);
   
      Class<?> serviceClass = serviceBean.getClass();
      String methodName = request.getMethodName();
      Class<?>[] parameterTypes = request.getParameterTypes();
      Object[] parameters = request.getParameters();
   
      /*Method method = serviceClass.getMethod(methodName, parameterTypes);
      method.setAccessible(true);
      return method.invoke(serviceBean, parameters);*/
      //为了避免使用 Java 反射带来的性能问题，我们可以使用 CGLib 提供的反射 API，如上面用到的FastClass与FastMethod
   
      FastClass serviceFastClass = FastClass.create(serviceClass);
      FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
      return serviceFastMethod.invoke(serviceBean, parameters);
    }
   
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
      System.out.println("System throw exception");
      ctx.close();
    }

}
