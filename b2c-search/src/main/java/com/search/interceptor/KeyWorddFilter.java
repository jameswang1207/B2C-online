package com.search.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class KeyWorddFilter implements HandlerInterceptor{

    /**
     * 整个请求结束之后，也就是在DispatcherServlet 渲染了对应的视图之后执行
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception exception)
            throws Exception {
        System.out.println("afterCompletion");
    }

    /**
     * 当前所属的Interceptor 的preHandle 方法的返回值为true 时才能被调用
     * postHandle 方法，顾名思义就是在当前请求进行处理之后，
     * 也就是Controller 方法调用之后执行，但是它会在DispatcherServlet 进行视图返回渲染之前被调用，
     * 所以我们可以在这个方法中对Controller 处理之后的ModelAndView 对象进行操作
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView modelAndView)
            throws Exception {
        System.out.println("postHandle");
    }
    
    /**
     * 该方法将在请求处理之前进行调用
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
        System.out.println("preHandle");
        String url = getBaseURL(request);
        System.out.println(url);
        return true;
    }
    
    private String getBaseURL(HttpServletRequest request) {
        String url = request.getScheme() 
                    + "://" 
                    + request.getServerName() 
                    + ":"
                    + request.getServerPort() 
                    + request.getContextPath() 
                    + request.getRequestURI();
        return url;
    }

}
