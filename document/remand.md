#前台开发
##架构
优点：
* 前台系统和服务层可以分开，降低系统的耦合度。
* 开发团队可以分开，提高开发效率
* 系统分开可以灵活的进行分布式部署。

缺点：
* 服务之间通信使用接口通信，开发工作量提高。
* 前台系统分为两部分，一部分是服务层web工程，功能就是发布服务
* 另外一部分：表现层，展示页面，没有业务逻辑。所有业务逻辑就是调用服务层的服务。

# 406：Springmvc中出现406
1. web.xml中使用这种配置。springmvc会将默认认为要返回html页面

```xml
    <!-- web page -->
    <servlet-mapping>
        <servlet-name>b2c-portal</servlet-name>
        <url-pattern>*.html</url-pattern>
    </servlet-mapping>
    <!--api -->
    <servlet-mapping>
        <servlet-name>b2c-portal</servlet-name>
        <url-pattern>*.api</url-pattern>
    </servlet-mapping>
```
2.
```java
    produces=MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8" 应该和@ResponseBody
```

#Redis 整合spring(要跟新的是根据不同的环境加载不同的配置文件)
1. 添加缓存时不能影响正常的业务逻辑

# solr 简介
