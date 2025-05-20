package org.wabc.sysadmin.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Collections;

/**
 * 代码生成器
 *
 * @author wabc
 * @version 1.0
 * @since 2023-12-31
 */
public class CodeGenerator {
    public static void main(String[] args) {
        String module = "/system";
        System.out.println(System.getProperty("user.dir") + module +  "/src/main/java");
        FastAutoGenerator.create("jdbc:mysql://192.168.182.131:3306/wabc?useSSL=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC",
                        "root", "dev123")
                // 全局配置
                .globalConfig(builder -> {
                    builder.author("wabc") // 设置作者
                            .commentDate("yyyy-MM-dd")   //注释日期
                            .outputDir(System.getProperty("user.dir") + module +  "/src/main/java") // 指定输出目录
                            .disableOpenDir() //禁止打开输出目录，默认打开
                    ;
                })
                // 包配置
                .packageConfig(builder -> {
                    builder.parent("org.wabc.system") // 设置父包名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                                    System.getProperty("user.dir")  + module + "/src/main/resources/mapper")); // 设置mapperXml生成路径
                })
                // 策略配置 "user", // .addInclude("oauth_client_details")
//                ,"sys_role","sys_menu","sys_permission","sys_dept",
//                "sys_user_role","sys_role_menu","sys_role_permission"
                .strategyConfig(builder -> {
                    builder.addInclude("oauth_client_details","sys_user","sys_role","sys_menu","sys_permission","sys_dept",
                "sys_user_role","sys_role_menu","sys_role_permission") // 设置需要生成的表名
//                            .addTablePrefix("sys_") // 设置过滤表前缀
                            // Entity 策略配置
                            .entityBuilder()
                            .enableLombok() //开启 Lombok
//                            .enableFileOverride() // 覆盖已生成文件
                            .naming(NamingStrategy.underline_to_camel)  //数据库表映射到实体的命名策略：下划线转驼峰命
                            .columnNaming(NamingStrategy.underline_to_camel)    //数据库表字段映射到实体的命名策略：下划线转驼峰命
                            // Mapper 策略配置
                            .mapperBuilder()
//                            .enableFileOverride() // 覆盖已生成文件
                            // Service 策略配置
                            .serviceBuilder()
//                            .enableFileOverride() // 覆盖已生成文件
                            .formatServiceFileName("%sService") //格式化 service 接口文件名称，%s进行匹配表名，如 UserService
                            .formatServiceImplFileName("%sServiceImpl") //格式化 service 实现类文件名称，%s进行匹配表名，如 UserServiceImpl
                            // Controller 策略配置
                            .controllerBuilder()
//                            .enableFileOverride() // 覆盖已生成文件
                    ;
                })
                .execute();

    }
}
