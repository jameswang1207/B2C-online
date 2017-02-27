# 相关技术点记录

## nginx install

```sh
   sudo apt-get install  nginx
   ln -s /usr/share/nginx/www/images /home/ftp/images
```

## ftp 安装　

* http://jingyan.baidu.com/article/7908e85c988b23af481ad2ae.html
* http://blog.csdn.net/duomoke/article/details/30263483

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


