# SpringSecurity6

# 参考


# 版本
jdk：17

spring-boot：3.0.1

spring-security-web；6.0.1

postgresql：42.5.1

hibernate-validator：8.0.0Final

querydsl：5.0.0

jakarta.validation-api：3.0.2

Redis：


# SQL

1、创建数据库，配置yml文件

2、maven 执行 clear 和 install 生成 QueryDsl 文件

3、启动项目自动创建表

4、插入数据：运行工程根目录下的SQL文件：`public.sql` 插入数据。

**数据说明：**

- 小黑
    - 系统超级管理员（system-super-role）
        - 添加：/api/shuishu/demo/test/add
        - 分页查询：/api/shuishu/demo/test/page
        - 详情：/api/shuishu/demo/test/details
        - 更新：/api/shuishu/demo/test/update
        - 删除：/api/shuishu/demo/test/delete

- 小白
    - 系统管理员（system-role）
        - 添加：/api/shuishu/demo/test/add
        - 分页查询：/api/shuishu/demo/test/page
        - 详情：/api/shuishu/demo/test/details
        - 更新：/api/shuishu/demo/test/update
- 小明
    - 普通用户（general-role）
        - 分页查询：/api/shuishu/demo/test/page
        - 详情：/api/shuishu/demo/test/details
- 小蓝
    - 普通用户（general-role）
        - 分页查询：/api/shuishu/demo/test/page
        - 详情：/api/shuishu/demo/test/details

`ss_user` 用户信息表，`ss_user_auth` 用户授权表（登录账号）。

一个用户可以有多种登录方式（系统本地账号、手机号、邮箱、微信），分别将账号信息单独抽取出来，与用户基本信息关联。


# apifox 接口文档

https://www.apifox.cn/apidoc/shared-8ef76ec3-a73a-4373-9b9e-5f56fa49a5a8/api-67709930



