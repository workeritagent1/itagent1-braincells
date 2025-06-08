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
## auth+system模块 oauth2.1认证授权方案基于静态html页面完成
```
auth+system模块 oauth2.1认证授权方案；使用Spring Authorization Server + OAuth 2.1 + OpenID Connect (OIDC) + JWT; 基于auth../resource/static下的静态html页面的授权认证模式完成。
| 步骤 | 参与者          | 关键动作                          | 代码/配置点                                                                                              | 技术说明                                                                                                     |
|------|----------------|-----------------------------------|----------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------|
| 1.   | 用户浏览器      | 访问 index.html                   | `GET http://localhost:10001/auth/index.html`                                                             | 前端首页加载                                                                                                |
				index.html             	--->	检查不是授权相关端点AuthorizationServerConfig:authorizationServerSecurityFilterChain:EndpointsMatcher(),
												检查defaultSecurityFilterChain:rest接口，没有对应，检查静态资源/index.html，存在即返回页面	
| 2.   | 用户浏览器      | 点击登录按钮生成PKCE参数          | index.html JavaScript代码：<br>`sha256()`生成code_challenge<br>`genRandomStr()`生成code_verifier         | PKCE(Proof Key for Code Exchange)防护授权码拦截攻击                                                        |
| 3.   | 用户浏览器      | 跳转授权端点                      | `GET /auth/oauth2/authorize?response_type=code&client_id=demo-client&...`                                | 携带PKCE参数发起OAuth2授权请求                                                                             |
				index.html-点击登录
					发起 授权请求/oauth2/authorize?callback----> 检查是授权相关端EndpointsMatcher()，并且没有授权成功，返回 重定向地址/login.html 
																[requestCache.saveRequest: 保存原始/oauth2/authorize?xxx原始请求到Session]
| 4.   | 授权服务器      | 拦截授权端点请求                  | AuthorizationServerConfig:<br>`authorizationServerSecurityFilterChain()`<br>`EndpointsMatcher`            | 安全链优先级1处理OAuth2端点                                                                                |
| 5.   | 授权服务器      | 检测未认证用户，重定向到登录页    | `LoginUrlAuthenticationEntryPoint("/login.html")`                                                        | 返回302重定向到登录页                                                                                      |
				重定向/login.html         ---> 检查端点、接口，返回静态资源/login.html
| 6.   | 用户浏览器      | 加载登录页面                      | `GET /auth/login.html`                                                                                   | 静态资源放行配置：<br>`defaultSecurityFilterChain()`<br>`antMatchers("/login.html").permitAll()`           |
| 7.   | 用户            | 提交登录表单                      | `POST /auth/login`<br>参数：username=demo&password=demo123456                                            | Spring Security表单登录端点                                                                                |
| 8.   | 授权服务器      | 执行认证逻辑                      | CustomAuthenticationProvider:<br>`authenticate()`<br>校验密码和账户状态                                  | 密码加密校验：<br>`passwordEncoder.matches()`                                                              |
| 9.   | 授权服务器      | 认证成功处理                      | `authenticationSuccessHandler()`<br>记录日志并重定向回授权端点                                           | 重建原始授权请求                                                                                            |
				login.html-输入用户名密码 
				发起 post请求/login      --->   formLogin..loginProcessingUrl("/login")处理...customAuthenticationProvider，认证成功后，
                                                从[requestCache.saveRequest: 保存原始请求到Session]中取出拿到原始/oauth2/authorize?xxx
| 10.  | 授权服务器      | 检查客户端设置                    | RegisteredClient配置：<br>`ClientSettings.requireAuthorizationConsent(true)`                             | 需要用户确认授权                                                                                            |                                                                     
												要求客户授权确认，返回授权确认页面
| 11.  | 授权服务器      | 生成授权确认页面                  | Spring SAS内置`ConsentPage`                                                                              | 展示请求的scope（openid, profile）                                                                         |
| 12.  | 用户            | 确认授权                          | 点击"Submit Consent"按钮                                                                                 | 用户同意授予权限                                                                                            |
| 13.  | 授权服务器      | 生成授权码                        | `JdbcOAuth2AuthorizationService`<br>写入oauth2_authorization表                                           | 授权码关联：<br>- client_id<br>- PKCE参数<br>- 用户身份                                                                 |
| 14.  | 授权服务器      | 重定向到回调地址                  | `302 Redirect`<br>`Location: http://localhost:10001/auth/callback.html?code=AUTHORIZATION_CODE`          | 携带授权码重定向                                                                                            |
| 15.  | 用户浏览器      | 处理回调页面                      | callback.html JavaScript代码：<br>`URLSearchParams`解析授权码                                            | 从URL提取授权码                                                                                             |
| 16.  | 用户浏览器      | 请求令牌端点                      | `POST /auth/oauth2/token`<br>Body参数：<br>`grant_type=authorization_code`<br>`code=AUTHORIZATION_CODE`<br>`code_verifier=...` | 交换令牌的关键请求                                                                                          |
| 17.  | 授权服务器      | 验证PKCE和授权码                  | `JdbcOAuth2AuthorizationService`<br>校验code_verifier的SHA256哈希                                        | 防止授权码拦截攻击                                                                                          |
| 18.  | 授权服务器      | 生成JWT令牌                       | JwtKeyConfig使用RSA密钥对：<br>- access_token<br>- id_token(OIDC)                                        | 令牌包含：<br>- 用户信息(AuthUserDetails)<br>- 权限(roles)                                                  |
| 19.  | 授权服务器      | 返回令牌响应                      | JSON响应：<br>`{ access_token, token_type, expires_in, id_token }`                                       | 符合OAuth2.1规范                                                                                            |
| 20.  | 用户浏览器      | 存储令牌                          | `localStorage.setItem("access_token", ...)`                                                              | 前端安全存储                                                                                                |
| 21.  | 用户浏览器      | 显示登录成功                      | callback.html更新DOM显示令牌信息                                                                         | 完成认证流程                                                                                                |

部分源码逻辑参考：OAuth2AuthorizationEndpointFilter的doFilterInternal方法中	
```
## html-demo情况和Spring Session Redis实现session共享说明：
```
1.如果是浏览器端作为oauth2_registered_client，用户登录，oauth2_registered_client表不能设置密码；
因为浏览器端的携带client密码会导致F12密码泄露；有风险。有code_challenge和自由login.html页面，足够安全；这是标准做法；
2.你的实际需求是本地只跑多个服务（如10001，10002…），不想用nginx负载均衡，直接用浏览器tab/窗口访问各端口，能否用Spring Session Redis实现session共享，
演示OAuth2流程？
答案是：
一、浏览器直接访问不同端口，能否验证Session共享？
结论：可以达到效果，只需注意细节，完全可验证Spring Session Redis分布式session共享功能。
原理说明
浏览器的cookie作用域是域名（host）+ path；端口不是区分cookie的组成部分。
3.oauth2.1认证授权方案；使用Spring Authorization Server + OAuth 2.1 + OpenID Connect (OIDC) + JWT 】最优做法
 1) OAuth 2.1标准强制所有授权码授权所有客户端，都必须使用PKCE
 2）Spring Session Redis是否必需？
   a. ①标准Web/表单授权流程（即使是前后端分离）,②全REST/JSON响应模式只要你用了授权码模式，并且SAS没有被你完全无状态改造，只要你用了授权码模式，并且SAS没有被你完全无状态改造，
 它还是会用Session来存授权中间态。此时如果是多实例服务/集群，Spring Session Redis还是必需的。
   b. 只有在你完全自定义实现所有认证授权接口，将所有状态转为前端/Token等存储，且Spring Authorization Server彻底无Session依赖时，才可以不用。
3. 最主流最优的OAuth 2.1规范做法（2025年主流实践）
 A. 必须做法
  授权码流（Authorization Code + PKCE）：适用于所有类型的前端、移动、服务端
  强制PKCE：所有客户端都要求
  OpenID Connect（OIDC）支持：身份ID Token认证
  JWT作为access_token/id_token：方便前后端无连接校验和分析
  HTTPS加密传输：所有接口必须
  客户端注册中心持久化：数据库/配置中心
  刷新令牌（refresh_token）轮换机制：防止重放，提升安全性
  范围（scope）控制 + 授权同意页
  资源服务器只用公钥/JWK/JWT本地验证，不查数据库
 B. 可选加固
  多因子认证（MFA）
  短时效access_token，长效refresh_token
  开启及时注销/撤销token端点
 C. 会话状态存储实践
  单实例或高可用：不用分布式session也可以，本地session就能保证。但是现代微服务/多实例就需Spring Session Redis这种方案，无论API风格或者前后端分离与否。
  如彻底追求无状态，必须重写SAS默认的HttpSession依赖点（生产极少使用）。
  4. 结论串联
  PKCE是OAuth 2.1授权码流必须，所有场景都不可省略。
  Spring Session Redis只有在你希望高可用、分布式时必须使用，即使RESTful、前后端分离，也要保证所有节点Session一致；单实例则可不用但失去高可用能力。
  最优做法是：授权码+PKCE+OIDC+JWT+HTTPS+（可选）MFA+令牌轮换+分布式session（如Redis）+最少权限/最短时效/客户端注册持久化。
  
4.注意将来强制扩展成https
5.auth模块包括entity:sysuser,sysrole,sysuserrole;虽然有点与system重复；但是为了内聚auth用户认证授权的逻辑；这样只增加了loadUserByName,和根据user查roles的极少量数据查询逻辑；方案很好。
 并且有简单的@select注解的sql，mybatis-plus不需要配置xml；
 
session状态共享示例：
同一个浏览器：
页面1： http://localhost:10001/auth/login.html  
 登录
页面2： http://localhost:10003/auth/oauth2/authorize?response_type=code&client_id=demo-client&redirect_uri=http%3A%2F%2Flocalhost%3A10001%2Fauth%2Fcallback.html&scope=openid+profile&code_challenge=AjdtRriakFAAyI0KcSBcKlMgQh8-c9CT3RyrcthwqMM&code_challenge_method=S256&state=yQbPteRJYWnfARZn
 勾选
页面1：http://localhost:10001/auth/callback.html?code=FAuPaq8Qy6OsagHXfPpaCm1dHe-ubEg_fCMT7UkqyA65WkqQJ9RTYLJvkJxhhnwloWbO10VXdwCEubxak5hx8Mm7Bi6a2MsPAlmCaBzM2sn3IFk7-966eybxGwmxive3&state=yQbPteRJYWnfARZn
获取token成功；

```