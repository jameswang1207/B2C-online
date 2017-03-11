# Tomcat热部署

1. Tomcat的配置
* 我们需要实现热部署,自然就需要通过maven操作tomcat,所以就需要maven取得操作tomcat的权限,配置tomcat的可操作权限.
* 在tomcat的安装目录下,修改conf / tomcat-user.xml文件,在<tomcat-users></tomcat-users>tomcat-users> 节点下面增加如下配置:

```xml
    <role rolename="manager-gui" />
    <role rolename="manager-script" />
    <user username="tomcat" password="tomcat" roles="manager-gui, manager-script"/>
```
2. 使用maven插件实现热部署
* 需要使用maven的tomcat插件。Apache官方提供的tomcat插件

3. 修改项目的pom.xml文件,在<build></build> 节点下面增加如下配置:tomcat7的配置(使用maven打包-->上传-->热部署-->气呵成)

```xml
<plugins>
    <!-- 配置Tomcat插件 -->
    <plugin>
        <groupId>org.apache.tomcat.maven</groupId>
        <artifactId>tomcat7-maven-plugin</artifactId>
        <configuration>
            <port>8080</port>
            <path>/</path>
            <url>http://192.168.222.114:8080/manager/text</url>
            <username>tomcat</username>
            <password>tomcat</password>
        </configuration>
    </plugin>
</plugins>
```
4. 热部署
* 热部署之前，修改配置文件中的数据库配置、调用服务的配置为生产环境需要的ip及端口。
* 执行以下命令：
* 初次部署可以使用 "tomcat7:deploy" 命令
* 如果已经部署过使用 "tomcat7:redeploy" 命令
* 部署跳过测试：tomcat7:redeploy -DskipTests
