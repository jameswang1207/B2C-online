# 相关技术点记录

## nginx install

* nginx是C语言开发，建议在linux上运行，本教程使用Centos6.5作为安装环境。
* gcc
  - 安装nginx需要先将官网下载的源码进行编译，编译依赖gcc环境，如果没有gcc环境，需要安装gcc：yum install gcc-c++ 
* PCRE
  - PCRE(Perl Compatible Regular Expressions)是一个Perl库,包括 nginx的http模块使用pcre来解析正则表达式，所以需要在linux上安装pcre库。
* yum install -y pcre pcre-devel
  - 注：pcre-devel是使用pcre开发的一个二次开发库。nginx也需要此库。
* zlib
   - zlib库提供了很多种压缩和解压缩的方式，nginx使用zlib对http包的内容进行gzip，所以需要在linux上安装zlib库。
* yum install -y zlib zlib-devel
* openssl
   - OpenSSL 是一个强大的安全套接字层密码库，囊括主要的密码算法、常用的密钥和证书封装管理功能及SSL协议，并提供丰富的应用程序供测试或其它目的使用。
   - nginx不仅支持http协议，还支持https（即在ssl协议上传输http），所以需要在linux安装openssl库。
* yum install -y openssl openssl-devel

* 将nginx压缩包拷贝至linux服务器。
* 解压：(baidu search install method......)

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
        "group": "主体",  //组名称
        "params": [ // 记录规格成员
            "品牌",
            "型号",
            "颜色",
            "上市年份",
            "上市月份"
        ]
    }，
    {
            "group": "网络",  //组名称
            "params": [ // 记录规格成员
                "4G",
                "3G,
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


