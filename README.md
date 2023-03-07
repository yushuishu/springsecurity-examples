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

运行SQL文件：`public.sql` 创建表结构，并插入数据

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
- 小红
    - 普通用户（general-role）
        - 分页查询：/api/shuishu/demo/test/page
        - 详情：/api/shuishu/demo/test/details

`ss_user` 用户信息表，`ss_user_auth` 用户授权表（登录账号）。

一个用户可以有多种登录方式（系统本地账号、手机号、邮箱、微信），分别将账号信息单独抽取出来，与用户基本信息关联。






