package org.wabc.system.models;

/**
 * models包是系统管理模块的普通实体对象集，是普通模块对象集划分包的典型代表。
 * base
 *      存放项目的基础类
 * entity
 *      实体对象包，用于存放直接映射到数据库表的持久化对象
 * po (Persistent Object)
 *      持久化对象包，与entity类似，用于存放与数据库表直接映射的对象。PO通常用于数据库的CRUD操作。
 * bo (Business Object)
 *      业务对象包，用于存放与业务逻辑直接相关的对象
 * dto (Data Transfer Object)
 *      数据传输对象包，用于存放在系统内部各层之间传输数据的对象。DTO通常用于表示客户端发送的请求或服务端响应的数据结构。
 * vo（View Object）
 *      VO用于封装将要显示在用户界面上的数据，它可以包含从多个数据库实体中聚合的数据
 * query
 *      查询对象包，用于存放用于查询的数据结构，这些结构可能包含多个字段用于构建复杂的查询条件
 *
 *  参考链接：https://zhuanlan.zhihu.com/p/645283758
 */
