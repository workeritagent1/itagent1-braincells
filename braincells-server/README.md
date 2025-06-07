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
  
  单独打包，commons一直找不到报错；需要外层父类打包
  https://blog.csdn.net/single_0910/article/details/120673729
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

## 项目结构
```

项目最推荐的包目录结构是怎样的？
braincells-server                         // 父工程  
├── pom.xml                              // 全局依赖和BOM版本管理  
├── commons/                             // 公共&工具模块  
├── auth/                                // 认证与授权服务 (原oauth2，建议更名为auth)  
├── gateway/                             // 网关服务  
├── system/                              // 系统管理服务 (原sysadmin，建议更名为system)  
└── [robot/]                             // 后续机器人扩展（预留）  

domain,entitymodel概念
domain（领域层/模型）  
└─ entity（实体，领域对象，最核心且有唯一Id）  
└─ value object（值对象，没有唯一Id，表达一个属性集合）  
└─ service（领域服务）  
└─ ... （其他领域组件）  
model（传递用的各种模型，包括 entity, DTO, VO, Result 等）  


commons模块示例：
com.wabc.commons  
├── core/  
│   ├── entity/          # 通用实体基类  
│   │   └── base/        # 审计、主键等基本父类  
│   ├── model/           # 通用响应、结果包装、分页、通用DTO/VO  
│   └── exception/       # 通用异常、异常枚举  
├── util/                # 各类工具类（如日期、加密、字符串、Bean、常用类型转换等）  
├── constant/            # 全局常量  
├── enums/               # 通用枚举（如状态枚举、性别、开关等）  
├── config/              # 公共配置类（Bean工具、全局配置、序列化等）  
├── annotation/          # 通用自定义注解  
├── handler/             # 通用全局处理（如全局异常处理、统一响应等）  
├── generator/           # **代码生成器**相关（如Mybatis-Plus Generator/Freemarker模板/配置/代码主类）  
│   ├── template/        # 代码生成模板  
│   ├── config/          # 生成器的配置  
│   └── xxxGenerator.java# 生成主逻辑  
└── validator/           # 通用参数/注解校验器  

system 系统管理模块 示例
com.wabc.system  
├── SystemApplication.java  
├── config/                  // Swagger、MyBatis、缓存、业务配置等  
├── controller/  
│   ├── UserController.java  
│   ├── RoleController.java  
│   ├── MenuController.java  
│   ├── DeptController.java  
│   └── ...  
├── service/  
│   └── impl/  
├── mapper/                  // MyBatis-Plus Mapper  
├── entity/                  // 业务实体  
│   ├── SysUser.java  
│   ├── SysRole.java  
│   ├── SysDept.java  
│   ├── SysMenu.java  
│   └── ...  
├── dto/                     // 参数对象  
├── vo/                      // 返回对象  
├── util/  
├── security/                // RBAC动态权限核心、上下文、登录上下文等  
├── exception/  
├── handler/                 // 全局异常、统一响应  
```

## 项目最基础的数据库骨架表
```
sys_user", "sys_role", "sys_dept", "sys_user_role", "sys_menu", "sys_role_menu", 
"auth_oauth2_client", "auth_oauth2_authorization"
```
## 代码生成器
```
[FullSmartCodeGenerator.java](commons%2Fsrc%2Fmain%2Fjava%2Forg%2Fwabc%2Fcommons%2Fcodegenerator%2FFullSmartCodeGenerator.java)
查看源代码可以打开文件覆盖。
```

## 配置文件
```
yml文件中有nacos config配置时，需要在nacos至少配置空文件，否则会报错。
wabc  gateway.yml
```

## 生成私钥
```
生成私钥：
openssl genpkey -algorithm RSA -out private_key.pem -pkeyopt rsa_keygen_bits:2048
生成公钥：
openssl rsa -in private_key.pem -pubout -out public_key.pem
用文本编辑器获取内容，安全管理！
```

##  oauth2.1标准表，名称不要修改
```
标准表结构下载地址 oauth2_registered_client，oauth2_authorization，oauth2_authorization_consent 
https://github.com/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/client/oauth2-registered-client-schema.sql

```

## 用户登录认证标准逻辑理解。
```
auth登录流程必须强制HTTPS,后期优化
最直白的登录认证授权流程
1. 用户操作流程
用户访问你的Vue后台管理页面（比如 http://localhost:8080/）。
发现没登录，页面上有个“登录”按钮。
用户点“登录”，页面会自动跳到公司的认证中心（比如http://auth.xxx.com/login）。
用户在认证中心页面输入账号、密码。
认证通过后，会自动跳回你的Vue页面（带着一个授权码code参数）。
Vue这时候用这个code参数，和之前浏览器里保存的PKCE校验码，一起发请求（POST）给认证中心，换“令牌token”。
Vue拿到token（JWT格式）后，保存起来，以后请求别的接口都带着这个token。

标准做法是让用户只在认证中心页面登录（login.html），其他页面没有表单，前端只是跳转跳板。
全世界大部分中大型系统已经是这种体验，并不会让用户疑惑。

传统体验：
[页面有账号/密码表单]
  |
[输入/登录]
  |
[进入系统]
OAuth2.1标准体验（你现在的方案）：
[页面只有“登录”按钮]
  |
[用户点击→自动跳转到官方认证中心地址]
  |
[这里才显示账号/密码/登录按钮]
  |
[输入后→自动跳回后台页面/主界面]

对于与传统的差异问题：
方案A：严守安全最佳实践，兼顾用户体验（行业主流）
UI/UX风格尽可能统一
让认证中心（login.html/认证服务login.vue）页面风格高度接近管理后台，保证跳转时视觉一致性，让用户直观感受“你还是在自己的系统”。
跳转时加友好提示，让用户清晰知道：“正在安全登录/这是公司官方登录平台”
登录按钮旁可标注：“将跳转到公司认证中心登录，保障账号安全”

```

## 认证、授权含义解释
```
认证：auth模块指定的登录页面输账号密码 → 查sys_user → 比对密码对就=认证通过。
授权：认证后/oauth2/authorize用户点“同意” → SAS生成code，并记入授权表 → 回跳原站。

 授权的两个概念：
 OAuth2.1 的“授权”（/oauth2/authorize）和后台“菜单按钮权限”是完全不同的两个授权场景，逻辑实现和关注点完全不同。
 1. /oauth2/authorize 的“授权”
 位置：认证中心（auth模块）
 作用：让用户“同意”前端应用获取自己身份，生成授权码/令牌
 级别：授权“应用”能代表你访问数据
 知识点：只管谁登录、允许哪些scope，比如 openid/profile，不管功能细节
 2. 菜单按钮权限（RBAC“授权”）
 位置：业务后端（system模块）
 作用：用户“能否访问后台具体功能/菜单/按钮”，即：页面/按钮/接口级权限精细控制
 级别：授权“用户”能执行什么具体操作
 知识点：通过JWT里的userId、roleId，查RBAC表（sys_user、sys_role、sys_menu、sys_role_menu等）
```
##  auth 模块的用户信息查询通过feign调用system;
```

内部微服务间调用涉及到四个类：
 1. FeignClient
 2.InternalApiSigner
 3.InternalApiAuthFilter
 4.FeignConfig
```

## 各模块端口
```
gateway 10000
auth 10001
system 10002
```

## html示例
```
二、核心逻辑流程
1. 用户访问 index.html
点击“登录”，生成 PKCE 参数，跳转 /oauth2/authorize，走授权码流程。
2. 未认证用户自动跳转 login.html
Spring Security 拦截，跳转 /login.html，用户输入账号密码。
3. 登录成功跳回 callback.html
携带授权码 code、state。
4. callback.html 用 code + PKCE 交换 token
通过 /oauth2/token POST 换取 access_token 和 id_token，完成登录。
5. 后续可用 access_token 访问 gateway/system 等API，网关负责JWT校验、权限路由。
==============================
示例：
1.index.html首页点击【登录】发起oauth2/authorize请求，引导跳转到auth模块的login.html
2.login.html页面输入用户密码登录；login后端接口根据登录情况返回授权页面，点击授权
2.
```