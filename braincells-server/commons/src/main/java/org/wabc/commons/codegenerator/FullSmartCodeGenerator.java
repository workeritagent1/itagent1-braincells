package org.wabc.commons.codegenerator;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 智能代码生成器
 * <p>
 * 支持企业级架构模式，自动生成 Entity、DTO、VO、Service、Mapper、Controller 等代码，并自动适配审计字段、逻辑删除，同时模板注释和参数灵活可配置。
 * </p>
 * <p>使用规范的模板加载方式，确保路径无拼写错误，维护性强，易于二次开发。 模板位置commons/.../resources/templates/ </p>
 * 代码生成器功能包括：
 * 1. 自动生成如下类，和大部分基础方法：
 * entity.java  带extends BaseAuditEntity；带@LogicDelete；
 * dto.java
 * page-dto.java
 * vo.java
 * mapper.java
 * mapper.xml
 * service.java
 * serviceImpl.java
 * controller.java
 * converter.java
 * 2.所有tinyint都设置为Integer，数据纯度高；而mybatis-plus默认是Byte，模板中有手动设置Integer,这样方便统一管理，扩展性强；也没有用枚举，枚举不方便数据迁移；
 * 3.带有openapi3的注解，方便前端联调；dto,controller有springboot-Validation参数校验相关注解。
 * 4.dto.java.ftl只对字符串类型加了1-length长度校验；page-dto.java.ftl不校验实体属性字段，根据需要再手工添加。
 * 5.controller使用严格的restful风格 @RequestMapping("/sys-users")
 * @author wabc
 * @date 动态生成
 */
public class FullSmartCodeGenerator {
    private static final Logger log = LoggerFactory.getLogger(FullSmartCodeGenerator.class);

    // ========= 【1. 数据库连接配置】 =========
    private static final String JDBC_URL = "jdbc:mysql://192.168.182.131:3306/2025wabc?useSSL=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "dev123";

    // ========= 【2. 特殊/逻辑字段名】 =========
    private static final Set<String> AUDIT_COLUMNS = new HashSet<>(Arrays.asList("created_by", "created_time", "updated_by", "updated_time")); // 审计字段
    private static final String LOGIC_DEL_FIELD = "deleted"; // 逻辑删除

    // ========= 【3. 基本配置】 =========
    private static final String PROJECT_PATH = System.getProperty("user.dir").replaceAll("\\\\", "/");

//    private static  String MODULE_NAME = "system";
    private static final String BASE_PACKAGE = "org.wabc";

    // ========= 【4. 代码结构包名】 =========
    private static final String ENTITY_PACKAGE = "entity";
    private static final String DTO_PACKAGE = "dto";
    private static final String VO_PACKAGE = "vo";
    private static final String CONVERTER_PACKAGE = "converter";
    private static final String MAPPER_PACKAGE = "mapper";
    private static final String SERVICE_PACKAGE = "service";
    private static final String SERVICE_IMPL_PACKAGE = "service.impl";
    private static final String CONTROLLER_PACKAGE = "controller";

    // ========= 【5. 注释作者及日期设置】 =========
    private static final String AUTHOR = "wabc";
    private static final String COMMENT_DATE = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    // ========= 【6. 模板路径优化点】 =========
    // 为了支持 FreemarkerTemplateEngine 默认 ClassPath 加载，使用相对路径
    private static final String TEMPLATE_ROOT = "templates/";  // 建议所有模板统一放 resources/templates 下

    // 项目最基础的数据库骨架表
    // List<String> tables = Arrays.asList("sys_user", "sys_role", "sys_dept", "sys_user_role", "sys_menu", "sys_role_menu", "auth_oauth2_client", "auth_oauth2_authorization");

    public static void main(String[] args) {
        log.info("代码生成器项目目录：" + PROJECT_PATH);

        // 生成system模块的基础代码
         List<String> systemTables = Arrays.asList("sys_user", "sys_role", "sys_dept", "sys_user_role", "sys_menu", "sys_role_menu");
        String system_module_name = "system";
        for (String table : systemTables) {
            log.info("==["+system_module_name+"]模块====代码生成器开始生成 [" + table + "] 的代码======");
            generateAll(table, null, system_module_name);
        }

        // 生成auth模块的基础代码，为了方便数据库管理，将outh模块的表加上了sys前缀
        List<String> outhTables = Arrays.asList( "auth_oauth2_client", "auth_oauth2_authorization");
        String auth_module_name = "auth";
        for (String table : outhTables) {
            log.info("==["+auth_module_name+"]模块====代码生成器开始生成 [" + table + "] 的代码======");
            generateAll(table, "auth_", auth_module_name);  // auth_前缀去掉，oauth2_xxx代码风格已经足够区分认证授权体系。
        }

    }

    /**
     * 代码生成主方法
     *
     * @param tableName   表名
     * @param tablePrefix 表名前缀（去除后生成驼峰名）
     */
    public static void generateAll(String tableName, String tablePrefix,String MODULE_NAME) {
        log.info("===== 当前模块：" + MODULE_NAME);

        boolean hasAudit = hasAllAuditColumns(tableName);
        boolean hasLogicDelete = hasLogicDelete(tableName);

        log.info("是否有审计字段: " + hasAudit);
        log.info("是否有逻辑删除字段: " + hasLogicDelete);

        // 各类文件输出路径 // itagent1-braincells\braincells-server\system\src\main\java\
        String javaSrcDir = PROJECT_PATH + "/" + MODULE_NAME + "/src/main/java/";
        String xmlResourcesDir = PROJECT_PATH + "/" + MODULE_NAME + "/src/main/resources/mapper/";
        // 自定义文件输出路径
        String customJavaSrcDir = PROJECT_PATH + "/" + MODULE_NAME + "/src/main/java/";

        log.info("Entity,Service...Java代码输出目录: " + javaSrcDir);
        log.info("Mapper XML输出目录: " + xmlResourcesDir);
        log.info("DTO,VO...自定义文件输出路径: " + customJavaSrcDir);
        log.info("模板资源路径（项目 classpath）: " + TEMPLATE_ROOT);

        FastAutoGenerator.create(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)
                .globalConfig(builder -> builder
                        .author(AUTHOR)
                        .outputDir(javaSrcDir) // itagent1-braincells\braincells-server\system\src\main\java\
                        .disableOpenDir()
                        .commentDate(COMMENT_DATE)
                        .enableSwagger()
                        .fileOverride()) // 添加全局文件覆盖配置; 全局覆盖已有文件的配置已失效，已迁移到策略配置中
                .packageConfig(builder -> builder
                        .parent(BASE_PACKAGE) //  "org.wabc";
                        .moduleName(MODULE_NAME) // "system";  // 完整java文件输出基础路径： javaSrcDir + BASE_PACKAGE + MODULE_NAME
                        .entity(ENTITY_PACKAGE)
                        .mapper(MAPPER_PACKAGE)
                        .service(SERVICE_PACKAGE)
                        .serviceImpl(SERVICE_IMPL_PACKAGE)
                        .controller(CONTROLLER_PACKAGE)
                        .pathInfo(Collections.singletonMap(OutputFile.xml, xmlResourcesDir)))
                .strategyConfig(builder -> {
                    builder.addInclude(tableName);
                    // 是否加前缀
                     if (StrUtil.isNotEmpty(tablePrefix)) {
                         builder.addTablePrefix(tablePrefix);
                     }
                    builder.entityBuilder()
                            .enableLombok()
                            .naming(NamingStrategy.underline_to_camel)
                            .columnNaming(NamingStrategy.underline_to_camel)
                            .enableChainModel()
                            .enableFileOverride()
                            .addTableFills(new Column("created_time", FieldFill.INSERT))
                            .addTableFills(new Column("updated_time", FieldFill.INSERT_UPDATE));
                    // ========= 【2. 特殊/逻辑字段名】 直接通过 ftl文件内部判断，此处不设置变量=========
//                    if (hasAudit) {
//                        builder.entityBuilder().superClass("org.wabc.commons.entity.BaseAuditEntity");
//                    }
//                    if (hasLogicDelete) {
//                        builder.entityBuilder().logicDeleteColumnName(LOGIC_DEL_FIELD)
//                                .logicDeletePropertyName(LOGIC_DEL_FIELD);
//                    }
                    builder.mapperBuilder().enableMapperAnnotation().enableBaseResultMap().enableBaseColumnList().enableFileOverride();
                    builder.serviceBuilder().formatServiceFileName("%sService").formatServiceImplFileName("%sServiceImpl").enableFileOverride();;
                    builder.controllerBuilder().enableRestStyle().formatFileName("%sController").enableHyphenStyle().enableFileOverride();
                })
                // ============ injectionConfig 仅用 classpath 下模板名，不用绝对物理路径 ===============
                .injectionConfig(builder -> {
                    String basePkgPath = customJavaSrcDir + (BASE_PACKAGE + "." + MODULE_NAME).replace(".", "/");
                    List<CustomFile> customFiles = Arrays.asList(
                            new CustomFile.Builder()
                                    .fileName("DTO.java")
                                    .templatePath(TEMPLATE_ROOT + "dto.java.ftl")
                                    .filePath(basePkgPath + "/dto")
                                    .enableFileOverride()
                                    .build(),
                            new CustomFile.Builder()
                                    .fileName("VO.java")
                                    .templatePath(TEMPLATE_ROOT + "vo.java.ftl")
                                    .filePath(basePkgPath + "/vo")
                                    .enableFileOverride()
                                    .build(),
                            new CustomFile.Builder()
                                    .fileName("PageDTO.java")
                                    .templatePath(TEMPLATE_ROOT + "page-dto.java.ftl")
                                    .filePath(basePkgPath + "/dto")
                                    .enableFileOverride()
                                    .build(),
                            new CustomFile.Builder()
                                    .fileName("Converter.java")
                                    .templatePath(TEMPLATE_ROOT + "converter.java.ftl")
                                    .filePath(basePkgPath + "/converter")
                                    .enableFileOverride()
                                    .build()
                    );
                    builder.customFile(customFiles);

                    builder.customMap(new HashMap<String, Object>() {{
                        String BASE_PACKAGE_MODULE=  BASE_PACKAGE + "." + MODULE_NAME;
                        put("entityPackage", BASE_PACKAGE_MODULE + "." + ENTITY_PACKAGE);
                        put("voPackage", BASE_PACKAGE_MODULE + "." + VO_PACKAGE);
                        put("dtoPackage", BASE_PACKAGE_MODULE + "." + DTO_PACKAGE);
                        put("converterPackage", BASE_PACKAGE_MODULE + "." + CONVERTER_PACKAGE);
                        put("controllerPackage", BASE_PACKAGE_MODULE + "." + CONTROLLER_PACKAGE);
                        put("mapperPackage", BASE_PACKAGE_MODULE + "." + MAPPER_PACKAGE);
                        put("servicePackage", BASE_PACKAGE_MODULE + "." + SERVICE_PACKAGE);
                        put("serviceImplPackage", BASE_PACKAGE_MODULE + "." + SERVICE_IMPL_PACKAGE);
                        //  ========= 【2. 特殊/逻辑字段名】 直接通过 ftl文件内部判断，此处不设置变量=========
//                        put("hasAudit", hasAudit);
//                        put("hasLogicDelete", hasLogicDelete);
                        put("author", AUTHOR);
                        put("date", COMMENT_DATE);
                    }});
                })
                .templateConfig(builder -> builder
                        .entity(TEMPLATE_ROOT + "entity.java") //不需要写.ftl文件后缀
                        .mapper(TEMPLATE_ROOT + "mapper.java") // .ftl
                        .xml(TEMPLATE_ROOT + "mapper.xml")
                        .service(TEMPLATE_ROOT + "service.java")
                        .serviceImpl(TEMPLATE_ROOT + "serviceImpl.java")
                        .controller(TEMPLATE_ROOT + "controller.java")
                )
                .templateEngine(new FreemarkerTemplateEngine()) // 使用 Freemarker，并用 Classpath资源
                .execute();
    }

    /** 判断表是否含全部审计字段 */
    private static boolean hasAllAuditColumns(String tableName) {
        return getTableColumns(tableName).containsAll(AUDIT_COLUMNS);
    }

    /** 判断表是否有逻辑删除字段 */
    private static boolean hasLogicDelete(String tableName) {
        return getTableColumns(tableName).contains(LOGIC_DEL_FIELD);
    }

    /** 获取表的所有字段 */
    private static Set<String> getTableColumns(String tableName) {
        Set<String> columns = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             ResultSet rs = conn.getMetaData().getColumns(null, null, tableName, null)) {
            while (rs.next()) columns.add(rs.getString("COLUMN_NAME").toLowerCase());
        } catch (SQLException e) {
            log.error("获取表字段出错: " + e.getMessage(), e);
        }
        return columns;
    }
}