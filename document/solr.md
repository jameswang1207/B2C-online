#Solr service install(solr-6.4.1)(192.168.222.115)
* http://blog.csdn.net/zcl_love_wx/article/details/52092098(solr页面操作详解)
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
* 在任意的目录下新建目录solr-home 如：(/home/james/solr-home)
* ${solr_home}/server/solr-webapp文件夹下有个webapp文件夹，将之复制到新建的目录下(/home/james/solr-home)
* ${solr_home}/server/lib/ext中的jar | ${solr_home}/dist中的jar全部复制到/home/james/solr-home/WEB-INF/lib 目录中
* ${solr_home}/server/resources/log4j.properties 复制到/home/james/solr-home/WEB-INF/classes目录中（如果没有classes则创建）
* /home/james/solr-home/WEB-INF创建solr_home文件夹，并将${solr_home}/example/example-DIH/solr/solr.xml拷贝到/home/james/solr-home/WEB-INF/solr_home/下
* /home/james/solr-home/WEB-INF/solr_home/下新建new_core文件夹，并将${solr_home}/server/solr/configsets/basic_configs/conf拷贝到该目录下

* 打开Tomcat/webapps/solr/WEB-INF下的web.xml，找到如下配置内容（初始状态下该内容是被注释掉的）：

```xml
<env-entry>
    <env-entry-name>solr/home</env-entry-name>
    <env-entry-value>/opt/solr</env-entry-value>
    <env-entry-type>java.lang.String</env-entry-type>
</env-entry>
```

* 设置tomcat的server.xml文件，在Host标签最后添加

```xml
    <Context docBase="/home/james/solr-home" path="/solr" reloadable="true"/>
```

* 保存关闭，而后启动tomcat，在浏览器输入http://localhost:8080/solr/index.html即可出现Solr的管理界面
* solr install is success.

4.中文分析器的配置
* 需要把分析器的jar包添加到solr工程中(ik-analyzer-solr5-5.x.jar)
* 需要把IKAnalyzer需要的扩展词典及停用词词典、配置文件复制到/home/james/solr-home/WEB-INF/classes中(ext.dic | IKAnalyzer.cfg.xml stopword.dic)
* 配置fieldType。需要在/home/james/solr-home/WEB-INF/solr_home/new_core/conf/managed-schema中配置

```xml
<fieldType name="text_ik" class="solr.TextField">
  <analyzer class="org.wltea.analyzer.lucene.IKAnalyzer"/>
</fieldType>
```
* 业务字段配置(managed-schema)
  业务字段判断标准：
  1.在搜索时是否需要在此字段上进行搜索。例如：商品名称、商品的卖点、商品的描述
  2.后续的业务是否需要用到此字段。例如：商品id

  * filed
  1.filed定义包括name,type（为之前定义过的各种FieldType）,indexed（是否被索引）,stored（是否被储存），multiValued（是否有多个值）等等
  2.field的定义相当重要，有几个技巧需注意一下，对可能存在多值的字段尽量设置multiValued属性为true，避免建索引是抛出错误；如果不需要存储相应字段值，尽量将stored属性设为false
  3.Query（查询页面）,查询的结果要显示哪个字段,就得将schema.xml文件配置字段时的stored属性设为true

  *copyField
  1.建议建立了一个拷贝字段，将所有的全文字段复制到一个字段中，以便进行统一的检索

```xml
    <field name="item_title" type="text_ik" indexed="true" stored="true"/>
    <field name="item_sell_point" type="text_ik" indexed="true" stored="true"/>
    <field name="item_price"  type="long" indexed="true" stored="true"/>
    <field name="item_image" type="string" indexed="false" stored="true" />
    <field name="item_category_name" type="string" indexed="true" stored="true" />
    <field name="item_desc" type="text_ik" indexed="true" stored="false" />
    <field name="item_keywords" type="text_ik" indexed="true" stored="false" multiValued="true"/>
    <copyField source="item_title" dest="item_keywords"/>
    <copyField source="item_sell_point" dest="item_keywords"/>
    <copyField source="item_category_name" dest="item_keywords"/>
    <copyField source="item_desc" dest="item_keywords"/>
```

# SolrCloud

### Zookeeper集群的搭建(192.168.222.118)
1.Zookeeper的安装步骤(在该机器上配置三个zk实例)
2.解压缩

```sh
tar -zxf zookeeper-3.4.6.tar.gz
```
3.在/home/james/目录下创建一个solrCloud目录。把zookeeper解压后的文件夹复制到此目录下三份。分别命名为zookeeper1,2,3并创建三个zkhome文件夹zkhome1,2,3作为zk数据存中心。
* 目录结构如下

```sh
zkhome1/
zkhome2/
zkhome3/
zookeeper1/
zookeeper2/
zookeeper3/
```
4. 分别在zkhome1,2,3建目录data和log并在data中创建myid文件其中的内容分别是1,2,3.

5.分别cp zookeeper1,2,3中conf下的zoo_sample.cfg并改名为zoo.cfg
6.编辑其中的内容(对应相应的目录)

```sh
  tickTime=2000
  dataDir=/home/james/solrCloud/zkhome{1|2|3}/data
  dataLogDir=/home/james/solrCloud/zkhome{1|2|3}/log
  initLimit=5
  syncLimit=2
  clientPort=218{1|2|3}
  # 投票端口：选举端口
  server.1=gcs-cloud-118:2881:3881
  server.2=gcs-cloud-118:2882:3882
  server.3=gcs-cloud-118:2883:3883
```
7.启动zookeeper。进入zookeeper1/bin目录下。
8.启动zookeeper：./zkServer.sh start
9.关闭：./zkServer.sh stop
10.查看状态：./zkServer.sh status
