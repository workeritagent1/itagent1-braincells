package org.worker.itagent1.auth.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Collections;

/**
 * @author itagent1.worker
 * @version 1.0
 * @ClassName GeneratorApp
 * @description:  代码生成器
 *  参考：https://blog.csdn.net/qq_42263280/article/details/126531993
 * @date 2023/6/4 18:14
 */
public class GeneratorApp {
    /**
     * 根据表名生成相应结构代码
     */
    public static void main(String[] args) {
//        String workdir=System.getProperty("user.dir") +"/auth";
//        String mapperDir="oauth";
//        String[] tableName={  "oauth_client_details","oauth_client_token","oauth_access_token",
//                "oauth_refresh_token","oauth_code","oauth_approvals"};

        String workdir=System.getProperty("user.dir") +"/auth";
        String mapperDir="admin";
        String[] tableName={  "system_user"};

        FastAutoGenerator.create("jdbc:mysql://192.168.182.131:3306/itagent?characterEncoding=UTF-8&useUnicode=true&useSSL=false",
                        "root", "dev123")
                // 全局配置
                .globalConfig(builder -> {
                    builder.author("itagent1.worker") // 设置作者
                            //启用swagger
                            .enableSwagger()
                            .commentDate("yyyy-MM-dd hh:mm:ss")   // 注释日期
                            .outputDir(workdir + "/src/main/java") // 指定输出目录
                            .disableOpenDir() // 禁止打开输出目录，默认打开
                    ;
                })
                // 包配置
                .packageConfig(builder -> {
                        builder.entity("entity")//实体类包名
                                .parent("org.worker.itagent1.auth." + mapperDir)//父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
                                .controller("controller")//控制层包名
                                .mapper("dao")//mapper层包名
                                .other("dto")//生成dto目录 可不用
                                .service("service")//service层包名
                                .serviceImpl("service.impl")//service实现类包名
                                //自定义mapper.xml文件输出目录
                                .pathInfo(Collections.singletonMap(OutputFile.mapperXml,workdir+"/src/main/resources/mapper/"+mapperDir));
                })

                .strategyConfig(builder -> {
                    builder.addInclude(tableName) // 设置需要生成的表名
                            // .addTablePrefix("sys_") // 设置过滤表前缀
                            // Entity 策略配置
                            .entityBuilder()
                            .enableLombok() //开启 Lombok
                            .enableChainModel()
                            .naming(NamingStrategy.underline_to_camel)  //数据表映射实体命名策略：默认下划线转驼峰underline_to_camel
                            .columnNaming(NamingStrategy.underline_to_camel)  //表字段映射实体属性命名规则：默认null，不指定按照naming执行
                            .idType(IdType.AUTO)//添加全局主键类型
                            .formatFileName("%s")//格式化实体名称，%s取消首字母I,
                            // Mapper 策略配置
                            .mapperBuilder()
                            .enableMapperAnnotation()//开启mapper注解
                            .enableBaseResultMap()//启用xml文件中的BaseResultMap 生成
                            .enableBaseColumnList()//启用xml文件中的BaseColumnList
                            .formatMapperFileName("%sMapper")//格式化Dao类名称
                            .formatXmlFileName("%sMapper")//格式化xml文件名称
                            // Service 策略配置
                            .serviceBuilder()

                            .formatServiceFileName("%sService") //格式化 service 接口文件名称，%s进行匹配表名，如 UserService
                            .formatServiceImplFileName("%sServiceImpl") //格式化 service 实现类文件名称，%s进行匹配表名，如 UserServiceImpl
                            // Controller 策略配置
                            .controllerBuilder()
                            .enableRestStyle();
                }).execute();

    }

}
