# 软件包下载地址
| 软件包名称                                              | 软件包官方版本查询和下载地址                                                                                                                                                                                 |  软件官方参考文档地址|
|----------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------|
| JDK8                                               | https://www.oracle.com/java/technologies/javase/javase8u211-later-archive-downloads.html  <br/> 使用bing搜索oracle免费账号登录下载                                                                         |   |
| Spring Cloud                                       | https://github.com/spring-cloud/spring-cloud-release/wiki                                                                                                                                      |    https://cloud.spring.io/  |
| Spring Cloud Alibaba                               | https://github.com/alibaba/spring-cloud-alibaba <br/> https://github.com/alibaba/spring-cloud-alibaba/blob/2022.x/README-zh.md <br/> https://github.com/alibaba/spring-cloud-alibaba/wiki/版本说明 | https://spring.io/projects/spring-cloud-alibaba#learn  <br/> https://spring-cloud-alibaba-group.github.io/github-pages/2021/zh-cn/index.html <br/> https://spring-cloud-alibaba-group.github.io/github-pages/2021/en-us/index.html| 
| nacos                                              | https://nacos.io/zh-cn/index.html                                                                                                                                                              | https://github.com/alibaba/nacos  |
| Spring Cloud + Spring Cloud Alibaba + nacos 版本组合参考 | https://github.com/alibaba/spring-cloud-alibaba/wiki/版本说明                                                                                                                                      |   |
| springboot                                         | https://spring.io/projects/spring-boot                                                                                                                                                         |   |
| Sentinel                                           | https://sentinelguard.io/zh-cn/                                                                                                                                                                | https://sentinelguard.io/zh-cn/docs/quick-start.html  |
| Maven Repository: Central                          | https://mvnrepository.com/repos/central                                                                                                                                                               | https://sentinelguard.io/zh-cn/docs/quick-start.html  |
# 启动步骤和命令
|步骤     | 启动                                                                                            |  备注|
|----------------|-----------------------------------------------------------------------------------------------|----------------------|
|第一步| 启动nacos windows单机 startup.cmd -m standalone <br/>  linux单机 sh startup.sh -m standalone <br/> http://192.168.1.7:8848/nacos/index.html | Linux/Unix/Mac sh shutdown.sh <br/>    Windows shutdown.cmd|

# 软件版本
| 软件名称和版本                       |备注和下载地址|
|-------------------------------|----------------------------------------------------------------------------------------------|
| Spring Cloud Version 2022.0.0.0-RC1 |  |
| Spring Cloud Alibaba Version 2022.0.0.0-RC1 |  |
| Spring Boot Version 2022.0.0.0-RC1 |  |
| Spring Boot Version 3.0.2 |  |
| Nacos Version 2.2.0 |  |
| Sentinel Version 1.8.6 |  |


# 问题清单
| 问题描述                                                             | 参考链接                                                                                                                                                   |  备注|
|------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------|
| idea快捷键导包不生效                                                     | https://blog.csdn.net/m0_57310021/article/details/121489630                                                                                            |设置idea自动导包|
| spring-cloud-starter-gateway和父pom.xml spring-boot-starter-web 冲突 | https://blog.csdn.net/zjh_746140129/article/details/128387122 <br/> https://blog.csdn.net/weixin_42668169/article/details/124938673                    |----------------------|
| github访问速度慢问题                                                    | https://zhuanlan.zhihu.com/p/93436925                                                                                                                  |----------------------|
| ideaIU-2022.3.3.exe激活码                                           | https://www.ajihuo.com/                                                                                                                                |----------------------|
| docker 安装mysql                                                   | https://blog.csdn.net/evak_/article/details/118785468 <br/> sudo docker run -p 3306:3306 --name mysql5.7_bc -e MYSQL_ROOT_PASSWORD=dev123 -d mysql:5.7 |----------------------|
| docker 安装redis                                                   | https://cloud.tencent.com/developer/article/1670205 <br/> sudo docker run -p 6379:6379 --name redis -v /data/redis/redis.conf:/etc/redis/redis.conf  -v /data/redis/data:/data -d redis redis-server /etc/redis/redis.conf --appendonly yes |----------------------|
| redis不能被外部ip访问                                                   | https://blog.csdn.net/dwwwwww/article/details/113820794 <br/> sudo docker run -p 6379:6379 --name redis -v /data/redis/redis.conf:/etc/redis/redis.conf  -v /data/redis/data:/data -d redis redis-server /etc/redis/redis.conf --appendonly yes |----------------------|
| idea配置类注释和方法注释                                                   | https://www.zhihu.com/question/507225066 |----------------------|
| ubuntu客户机访问win11宿主机ping不通，关闭win11防火墙即可                           | |----------------------|
| ubuntu安装openjdk8                                                 | sudo apt-get install openjdk-8-jre|----------------------|

## 类注释和方法注释模板
```
/**
 * @ClassName ${NAME}
 * @description: 
 * @author itagent1.worker
 * @date ${DATE} ${TIME}
 * @version 1.0
 */
 
 /** 
 * @description: $description$ 
 * @param: $params$ 
 * @return: $returns$ 
 * @author itagent1.worker
 * @date: $date$ $time$
 */
```
# 重要命令
| 命令                                                                                                                                               ||
|--------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------|
| git添加所有文件再提交: git add --all                                                                                                                      |  |
| 生产jwt.jks密钥命令【keytool -genkeypair -alias jwt -keyalg RSA -keysize 2048 -validity 1200 -keypass Wabc@2023 -keystore jwt.jks -storepass Wabc@2023】 | https://www.macrozheng.com/cloud/gateway_oauth2.html#micro-oauth2-auth |
| Cannot find module 'D:\WorkSpace\youlai\mall-admin\node_modules\vite\bin\vite.js'                                                                |  |

keytool -genkey -alias jwt -keyalg RSA -keypass dev123 -keystore jwt.jks -storepass dev123

# 相关服务划分：
```
micro-oauth2-gateway：网关服务，负责请求转发和鉴权功能，整合Spring Security+Oauth2；OAuth2资源服务器是提供给客户端资源的服务器，有验证token的能力，token有效则放开资源，
micro-oauth2-auth：Oauth2认证服务，负责对登录用户进行认证，整合Spring Security+Oauth2；
micro-oauth2-api：受保护的API服务，用户鉴权通过后可以访问该服务，不整合Spring Security+Oauth2。
包名称：wabc ==> worker agent brain cell 
```

## 
```
为了减少盗用和窃取，JWT不建议使用HTTP协议来传输代码，而是使用加密的HTTPS协议进行传输。

凡事纠结的地方，以少花时间为准
```