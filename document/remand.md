# 相关技术点记录（ubuntu14.04）

## ftp service install

```sh
   sudo apt-get install  nginx
   ln -s /usr/share/nginx/www/images /home/ftp/images
```

## ftp 安装　

* http://jingyan.baidu.com/article/7908e85c988b23af481ad2ae.html
* http://blog.csdn.net/duomoke/article/details/30263483

#　nginx介绍
*　Nginx是一款高性能的http 服务器/反向代理服务器及电子邮件（IMAP/POP3）代理服务器。由俄罗斯的程序设计师IgorSysoev所开发，官方测试nginx能够支支撑5万并发链接，并且cpu、内存等资源消耗却非常低，运行非常稳定

＃　Nginx的应用场景
* http服务器。Nginx是一个http服务可以独立提供http服务。可以做网页静态服务器.
* 虚拟主机。可以实现在一台服务器虚拟出多个网站。例如个人网站使用的虚拟主机
* 反向代理，负载均衡。当网站的访问量达到一定程度后，单台服务器不能满足用户的请求时，需要用多台服务器集群可以使用nginx做反向代理。并且多台服务器可以平均分担负载，不会因为某台服务器负载高宕机而某台服务器闲置的情况。

# nginx安装

```sh
    # nginx install
    sudo apt-get install nginx
    # nginx stop | up | restart
    sudo service nginx stop
    sudo service nginx start
    sudo service nginx restart
```

#配置虚拟主机

##什么是虚拟主机
* 虚拟主机是一种特殊的软硬件技术，它可以将网络上的每一台计算机分成多个虚拟主机，每个虚拟主机可以独立对外提供www服务，这样就可以实现一台主机对外提供多个web服务，每个虚拟主机之间是独立的，互不影响的。
* 通过nginx可以实现虚拟主机的配置，nginx支持三种类型的虚拟主机配置.(基于ip的虚拟主机|基于域名的虚拟主机|基于端口的虚拟主机)

### 基于ip的虚拟主机
* ifconfig 查看自己的ip（eth1有ip:192.168.222.114）

```sh
    #编辑 /etc/network/interfaces 原来内容为：
    auto lo
    iface lo inet loopback
```

```sh
    #编辑 /etc/network/interfaces 后内容为：
    auto lo
    iface lo inet loopback

    auto eth1:0
    iface eth1:0 inet static

    address 192.168.222.246
    netmask 255.255.255.0
```

```sh
    #让该ｉｐ生效（注　sudo /etc/init.d/networking restart　该方式在 14.04 LTS 中使用无效。）
    sudo ifup eth1:0
```

### 测试
1. Config nginx

```sh
vi /etc/nginx/conf.d/b2c.conf
```

2. Add below configuration to the file **Change the folder name to your own project (/usr/share/nginx/www/html1)

```sh
    server {
    #监听的ip和端口，配置192.168.101.3:80
        listen       80;
    #虚拟主机名称这里配置ip地址
        server_name  192.168.222.114;
    #所有的请求都以/开始，所有的请求都可以匹配此location
        location / {
        #使用root指令指定虚拟主机目录即网页存放目录

            root   /usr/share/nginx/www/html1;
        #指定欢迎页面，按从左到右顺序查找
            index  index.html index.htm;
        }

    }
    #配置虚拟主机192.168.101.103
    server {
        listen       80;
        server_name  192.168.222.246;

        location / {
            root   /usr/share/nginx/www/html2;
            index  index.html index.htm;
        }
    }
```

### 基于域名的虚拟主机
* 两个域名指向同一台nginx服务器，用户访问不同的域名显示不同的网页内容。两个域名是aaa.test.com和bbb.test.com,nginx服务器使用虚拟机192.168.222.114,192.168.222.246

* 通过(/etc/hosts)文件指定aaa.test.com和bbb.test.com对应192.168.222.114,192.168.222.246虚拟机：

### 测试
1. Config nginx

```sh
vi /etc/nginx/conf.d/b2c.conf
```

2. Add below configuration to the file **Change the folder name to your own project (/usr/share/nginx/www/html1)

```sh
    server {
    #监听的ip和端口，配置192.168.101.3:80
        listen       80;
    #虚拟主机名称这里配置ip地址
        server_name  aaa.test.com;
    #所有的请求都以/开始，所有的请求都可以匹配此location
        location / {
        #使用root指令指定虚拟主机目录即网页存放目录

            root   /usr/share/nginx/www/html1;
        #指定欢迎页面，按从左到右顺序查找
            index  index.html index.htm;
        }

    }
    #配置虚拟主机192.168.101.103
    server {
        listen       80;
        server_name  bbb.test.com;

        location / {
            root   /usr/share/nginx/www/html2;
            index  index.html index.htm;
        }
    }
```
3.访问aaa.test.com、bbb.test.com

## nginx反向代理
```sh
   chmod -R 7777 /opt/
   chown -R james:james /opt
```
1. Nginx只做请求的转发，后台有多个http服务器提供服务，nginx的功能就是把请求转发给后面的服务器，决定把请求转发给谁

2. 安装tomcat
* 在一个虚拟机上创建两个tomcat实例，模拟多个服务器。
* 注意修改两个端口

３．需求
* 通过访问不同的域名访问运行在不同端口的tomcat
* test.tomcat1.com   访问运行8081端口的tomcat
* test.tomcat2.com   访问运行8082端口的tomcat

4. 域名需要配置host文件

5. 添加文件/etc/nginx/conf.d
* touch balance.conf

* 配置文件

```sh
    upstream tomcatserver1 {
        server 192.168.222.114:8081;
    }
    upstream tomcatserver2 {
        server 192.168.222.114:8082;
    }

    server {
        listen       80;
        server_name  test.tomcat1.com;

        location / {
            proxy_pass   http://tomcatserver1;
            index  index.html index.htm;
        }
    }

    server {
        listen       80;
        server_name  test.tomcat2.com;
        location / {
            proxy_pass   http://tomcatserver2;
            index  index.html index.htm;
        }
    }
```

## 如果在同一个域名下有多台服务器提供服务，此时需要nginx负载均衡
1.什么是负载均衡
* 负载均衡 建立在现有网络结构之上，它提供了一种廉价有效透明的方法扩展网络设备和服务器的带宽、增加吞吐量、加强网络数据处理能力、提高网络的灵活性和可用性。负载均衡，英文名称为Load Balance，其意思就是分摊到多个操作单元上进行执行，例如Web服务器、FTP服务器、企业关键应用服务器和其它关键任务服务器等，从而共同完成工作任务。

2.需求

* nginx作为负载均衡服务器，用户请求先到达nginx，再由nginx根据负载配置将请求转发至 tomcat服务器。
* nginx负载均衡服务器：192.168.222.114
* tomcat1服务器：192.168.222.114:8081
* tomcat2服务器：192.168.222.114:8082

3. nginx config

```sh
    upstream tomcatserver1 {
        server 192.168.222.114:8081;
        server 192.168.222.114:8082;
    }
    upstream tomcatserver2 {
        server 192.168.222.114:8082;
    }

    server {
        listen       80;
        server_name  test.tomcat1.com;

        location / {
            proxy_pass   http://tomcatserver1;
            index  index.html index.htm;
        }
    }

    server {
        listen       80;
        server_name  test.tomcat2.com;
        location / {
            proxy_pass   http://tomcatserver2;
            index  index.html index.htm;
        }
    }
```

4. 配置负载均衡的权重
```sh
    upstream tomcatserver1 {
        server 192.168.222.114:8081 weight=3;
        server 192.168.222.114:8082 weight=1;
    }
    upstream tomcatserver2 {
        server 192.168.222.114:8082;
    }

    server {
        listen       80;
        server_name  test.tomcat1.com;

        location / {
            proxy_pass   http://tomcatserver1;
            index  index.html index.htm;
        }
    }

    server {
        listen       80;
        server_name  test.tomcat2.com;
        location / {
            proxy_pass   http://tomcatserver2;
            index  index.html index.htm;
        }
    }
```

5.节点说明
在http节点里添加:定义负载均衡设备的 Ip及设备状态

```sh
upstream myServer {

    server 127.0.0.1:9090 down;
    server 127.0.0.1:8080 weight=2;
    server 127.0.0.1:6060;
    server 127.0.0.1:7070 backup;
}
```

* down 表示单前的server暂时不参与负载
* weight  默认为1.weight越大，负载的权重就越大。
* max_fails ：允许请求失败的次数默认为1.当超过最大次数时，返回proxy_next_upstream 模块定义的错误
* fail_timeout:max_fails 次失败后，暂停的时间。
* backup： 其它所有的非backup机器down或者忙的时候，请求backup机器。所以这台机器压力会最轻。


## 商品信息和商品描述分开存储，因为商品描述是大文本信息

## 商品規格********************************************

###商品规格使模板方法：优点
* 不需要做多表管理。
* 如果要求新添加的商品规格项发生改变，之前的商品不变是很简单的。

###缺点
* 复杂的表单和json之间的转换。对js的编写要求很高

###模板方法案例
* 每一个商品分类对一个规格参数模板。

```json
[
    {
        "group": "主体",
        "params": [
            "品牌",
            "型号",
            "颜色",
            "上市年份",
            "上市月份"
        ]
    },
    {
            "group": "网络",
            "params": [
                "4G",
                "3G",
                "2G"
            ]
    }
]
```
* 每个商品对应一唯一的规格参数。在添加商品时，可以根据规格参数的模板。生成一个表单。保存规格参数时。还可以生成规格参数的json数据。保存到数据库中。(使用方法)

```json
[
    {
        "group": "主体",
        "params": [
            {
                "k": "品牌",
                "v": "苹果（Apple）"
            },
            {
                "k": "型号",
                "v": "iPhone 6 A1589"
            },
            {
                "k": "智能机",
                "v": "是 "
            }
        ]
    }
]
```

###关联表查询缺点
* 需要创建多张表来描述规格参数之间的关系。
* 查询时需要复杂的sql语句查询。
* 规格参数数据量是商品信息的几十倍，数据量十分庞大。查询时效率很低。
* 如果要求新添加的商品规格项发生改变，之前的商品不变是不能实现的。

```sql
    SELECT
        pg.group_name,pk.param_name,pv.param_value
    FROM
        tb_item_param_value pv
    LEFT JOIN tb_item_param_key pk ON pv.param_id = pk.id
    LEFT JOIN tb_item_param_group pg ON pk.group_id = pg.id
    WHERE
        item_id = 855739
```

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


