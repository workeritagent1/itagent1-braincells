# 组件版本
|软件名称和版本|
|----------------|
|jdk:17|
|spring-boot:3.0.2|
|spring.cloud:2022.0.1|
|spring-cloud-alibaba:2022.0.0.0-RC1|
|nacos-server:2.2.0|
|Sentinel:1.8.6|


# 软件包下载地址
```
JDK17	https://www.oracle.com/java/technologies/downloads/#jdk17-windows
nacos	2.1.1 
  
    
    // change the $version to your actual path
    cd distribution/target/nacos-server-$version/nacos/bin
sentinel
```
# 教程参考链接
```
https://spring.io/projects/spring-cloud
https://spring-cloud-alibaba-group.github.io/github-pages/2021/en-us/index.html

spring-cloud-alibaba地址： https://spring-cloud-alibaba-group.github.io/github-pages/hoxton/zh-cn/index.html
nacos   https://nacos.io/zh-cn/docs/quick-start.html
    https://nacos.io/zh-cn/docs/quick-start.html
    lib/nacos-2.1.1.zip源码
    mvn -Prelease-nacos -Dmaven.test.skip=true clean install -U  
    ls -al distribution/target/    
    linux单机 sh startup.sh -m standalone
    windows单机 startup.cmd -m standalone
    nacos下载地址：linux  https://github.com/alibaba/nacos/releases/download/2.1.1/nacos-server-2.1.1.tar.gz
                   window https://github.com/alibaba/nacos/releases/download/2.1.1/nacos-server-2.1.1.zip
                   
           参考技术：https://github.com/mtcarpenter/mall-cloud-alibaba
           
           
      https://jwt.io/     
```




# 问题清单
```
IDEA Unable to import maven project: See logs for details具体解决方法
    https://blog.csdn.net/qq_43516594/article/details/109175115
springboot + springcloud + spring cloud alibaba+nacos版本不符的问题
    https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E
    
问题：前端/oauth/token报401,403 问题报401/403   
   后端： 
2024-03-10 12:21:01.683 DEBUG 58776 --- [nio-8002-exec-2] s.s.w.c.SecurityContextPersistenceFilter : Cleared SecurityContextHolder to complete request
2024-03-10 12:21:01.689 ERROR 58776 --- [nio-8002-exec-2] o.a.c.c.C.[.[.[.[dispatcherServlet]      : Servlet.service() for servlet [dispatcherServlet] in context with path [/authorization] threw exception [Filter execution threw an exception] with root cause
java.lang.StackOverflowError: null
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:344) ~[spring-aop-5.3.31.jar:5.3.31]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:198) ~[spring-aop-5.3.31.jar:5.3.31]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163) ~[spring-aop-5.3.31.jar:5.3.31]
  1.没有加 @Qualifier("ClientDetailsServiceImpl")，走到其他ClientDetailsService实现类了，一直达不到断点。
  2. 没有设置密码，参数遗漏； clientDetails.setClientSecret(oauthClient.getClientSecret());	
  AbstractUserDetailsAuthenticationProvider 调试类
```

# 不要用什么最新版，当小白鼠，太浪费事件时间。

##
```
2023-12-23 19:00 [authorization]模块单独启动成功
启用数据库服务
nacos-server-2.2.0\nacos\bin>startup.cmd -m standalone
AuthorizationApplication.java
```
http://localhost:10000/apigateway/authorization/oauth/token?client_id=client_id_01&client_secret=$10$ExMtq3Z1FPp6nD62uf//AuDXLU.OYHP5AOtvX14WzeiiCM2kVld3.&grant_type=password&username=admin&password=123456

http://localhost:8002/authorization/oauth/token?client_id=client_id_01&client_secret=$10$ExMtq3Z1FPp6nD62uf//AuDXLU.OYHP5AOtvX14WzeiiCM2kVld3.&grant_type=password&username=admin&password=123456

http://localhost:8001/system/

http://localhost:10000/gateway/system/sysPermission/loadPermissionRoles
http://localhost:8001/system/sysPermission/loadPermissionRoles


https://youlai.blog.csdn.net/article/details/108758828

https://www.cnblogs.com/haoxianrui/p/13719356.html


1.A页面，点击按钮，生成一个授权请求，重定向到B的授权端点-（授权端点跳转到登录页面）
2.用户在B页面登录（携带A的重定向信息），B页面询问是否授权A获取个人资料。
3.用户同意后，B生成授权码，返回给A的重定向url.
4.A使用授权码、客户端标识、秘钥等参数，POST请求发给B的令牌端点。
5.B验证授权码和标识的有效性，颁发令牌给A.
6.A使用令牌代表用户请求B受保护的资源访问，B验证令牌并根据权限授权拒绝或同意对资源的访问。


那么，OAuth2 资源服务器结合 JWT 的作用是什么呢？

身份验证：资源服务器使用 JWT 来验证请求中的访问令牌。它会对令牌进行解析，并检查签名和有效期等信息，以确保令牌的可信度和有效性。
授权访问：JWT 中的负载部分可以包含用户的权限信息。资源服务器使用这些信息来控制对受保护资源的访问权限，确保只有具有适当权限的用户能够访问。
通过结合 OAuth2 资源服务器和 JWT，我们可以实现对受限资源的安全保护和访问控制。OAuth2 提供了授权机制和流程，而 JWT 提供了一种可靠的身份验证和授权数据传输方式。


最佳推荐的文章：https://blog.csdn.net/NeverFG/article/details/131405161
 源代码： https://github.com/torlesse-liang/torlesse-oauth2
 https://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html
https://blog.csdn.net/qq_42264638/article/details/131289529
AuthorizationEndpoint
TokenEndpoint
## debug
```
先比对client,再比对user
additionalAuthenticationChecks
postman :
http://localhost:8002/authorization/oauth/token
Basic auth  
    Username    client01
    Password    123456
body x-www-form-urlencoded
    grant_type password
    username admin
    password 123456    
```

## 