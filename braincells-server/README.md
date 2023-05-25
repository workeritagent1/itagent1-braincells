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
```


# 问题清单
```
IDEA Unable to import maven project: See logs for details具体解决方法
    https://blog.csdn.net/qq_43516594/article/details/109175115
springboot + springcloud + spring cloud alibaba+nacos版本不符的问题
    https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E
```

# 不要用什么最新版，当小白鼠，太浪费事件时间。

