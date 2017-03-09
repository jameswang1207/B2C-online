## maven 配置(3.3.9)
* 配置maven国内对象(阿里云)

```xml
    <!-- 配置maven本地仓库 -->
    <localRepository>/opt/maven-repository</localRepository>
    <!-- services 中添加 -->
    <server>
        <id>releases</id>
        <username>ali</username>
        <password>ali</password>
    </server>
    <server>
        <id>Snapshots</id>
        <username>ali</username>
        <password>ali</password>
    </server>

    <mirrors>
        <mirror>
            <id>nexus</id>
            <mirrorOf>*</mirrorOf>
            <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
        </mirror>
        <mirror>
            <id>nexus-public-snapshots</id>
            <mirrorOf>public-snapshots</mirrorOf>
            <url>http://maven.aliyun.com/nexus/content/repositories/snapshots/</url>
        </mirror>
    </mirrors>
```

