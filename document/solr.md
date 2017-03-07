#Solr service install(solr-6.4.1)
1. 什么是Solr
    Solr 是Apache下的一个顶级开源项目，采用Java开发，它是基于Lucene的全文搜索服务器。Solr提供了比Lucene更为丰富的查询语言，同时实现了可配置、可扩展，并对索引、搜索性能进行了优化。
    Solr可以独立运行，运行在Jetty、Tomcat等这些Servlet容器中，Solr 索引的实现方法很简单，用 POST 方法向 Solr 服务器发送一个描述 Field 及其内容的 XML 文档，Solr根据xml文档添加、删除、更新索引 。Solr 搜索只需要发送 HTTP GET 请求，然后对Solr返回Xml、json等格式的查询结果进行解析，组织页面布局。Solr不提供构建UI的功能，Solr提供了一个管理界面，通过管理界面可以查询Solr的配置和运行情况。

2. Solr 目录结构
* bin：是脚本的启动目录
* contrib：第三方包存放的目录
* dist：编译打包后存放目录，即构建后的输出产物存放的目录
* docs：solr文档的存放目录
* example：示范例子的存放目录，这里展示了DIH，即数据导入处理的例子
* server：即solr搜索引擎框架，基于jetty web服务器开发的。包含jetty服务器的配置。(这个目录就类似于一个包含了tomcat服务器，里面有一个基于solr的web工程)
    contexts：jetty的环境
    etc：jetty的配置文件
    lib：jetty服务器的jar包
    modules：jetty的启动模式
    resources：资源文件
    scripts：脚本文件
    solr：solr服务器的配置文件，solr基于jetty服务器开发的
    solr-webapp：solr的web工程
    start.jar：启动jar包。通过Java命令就可以启动一个基于jetty服务器的web工程

3.Solr整合tomcat
* 将 solr 压缩包中 ${solr_home}/server/solr-webapp文件夹下有个webapp文件夹，将之复制到Tomcat\webapps\目录下，并改成solr (名字随意，通过浏览器进行访问solr管理界面时要用到）
* 将solr压缩包中 ${solr_home}/server/lib/ext中的jar全部复制到 Tomcat\ webapps\solr\WEB-INF\lib 目录中
* 将 solr 压缩包中 ${solr_home}/server/resources/log4j.properties 复制到Tomcat\ webapps\solr\WEB-INF\classes目录中（如果没有classes则创建）
* 将 solr 压缩包中 solr-5.4.0/server/solr 目录复制到计算机某个目录下
* 打开Tomcat/webapps/solr/WEB-INF下的web.xml，找到如下配置内容（初始状态下该内容是被注释掉的）：

```xml
<env-entry>
    <env-entry-name>solr/home</env-entry-name>
    <env-entry-value>/opt/solr</env-entry-value>
    <env-entry-type>java.lang.String</env-entry-type>
</env-entry>
```
* 打开Tomcat/webapps/solr/WEB-INF下的web.xml修改项目欢迎页面

```xml
  <welcome-file-list>
    <welcome-file>./index.html</welcome-file>
  </welcome-file-list>
```
* 还需要添加solr-dataimporthandler-5.4.0.jar和solr-dataimporthandler-extras-5.4.0.jar这2个jar包到目录/usr/local/tomcat/tomcat-8.0.30/webapps/solr/WEB-INF/lib/下，否则会报错，这2个包默认不在webapp里，在下载包的dist目录下
* 保存关闭，而后启动tomcat，在浏览器输入http://localhost:8080/solr即可出现Solr的管理界面
