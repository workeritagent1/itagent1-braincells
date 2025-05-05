//package org.wabc.commons.codegenerator;
//
//import com.baomidou.mybatisplus.annotation.FieldFill;
//import com.baomidou.mybatisplus.generator.FastAutoGenerator;
//import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
//import com.baomidou.mybatisplus.generator.fill.Column;
//
//import java.sql.*;
//import java.util.*;
//
///**
// * 通用后台管理系统 代码生成器：
// * 1. 按数据库实际表自动判断是否有审计字段决定继承BaseAuditEntity
// * 2. 支持create_time/update_time自动填充
// * 3. 支持deleted字段逻辑删除
// * 4. 自动过滤表前缀（如sys_生成User等）
// * 5. 代码生成模板直接用MyBatis-Plus默认，兼容Knife4j+SpringDoc
// */
//public class CodeGenerator {
//
//    // 数据源配置：建议临时放置，敏感信息生产中严禁硬编码
//    private static final String URL = "jdbc:mysql://localhost:3306/your_db?useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
//    private static final String USERNAME = "root";
//    private static final String PASSWORD = "your_password";
//
//    // 待生成代码的数据库表 // sys_dept，sys_user，sys_role，sys_user_role，sys_menu，sys_role_menu，sys_oauth2_client，sys_oauth2_authorization
//    private static final List<String> TABLES = Arrays.asList("sys_dept", "sys_user", "sys_role","sys_user_role",
//                    "sys_menu", "sys_role_menu",
//                    "sys_oauth2_client", "sys_oauth2_authorization");
//
//    // 代码生成目标包名，可自定义
//    private static final String BASE_PACKAGE = "org.wabc.sysadmin";
//
//    // 公共审计字段名集合
//    private static final Set<String> AUDIT_FIELDS = new HashSet<>(Arrays.asList(
//            "created_by", "created_time", "updated_by", "updated_time"
//    ));
//
//    // 逻辑删除字段名
//    private static final String LOGIC_DEL_FIELD = "deleted";
//
//    // 表名前缀过滤
//    private static final String TABLE_PREFIX = "sys_";
//
//    // 基类完全限定名
//    private static final String BASE_AUDIT_ENTITY = "org.wabc.commons.model.entity.BaseAuditEntity";
//
//    public static void main(String[] args) throws Exception {
//        // 动态分析所有表: 是否有全部审计字段
//        List<String> allTables = TABLES;
//        Map<String, Boolean> auditMap = new HashMap<>();
//        for (String tbl : allTables) {
//            auditMap.put(tbl, hasAllAuditFields(tbl));
//        }
//
//        FastAutoGenerator.create(
//                        new DataSourceConfig.Builder(URL, USERNAME, PASSWORD)
//                )
//                .globalConfig(builder -> builder
//                        .author("AutoGenerator")
//                        .commentDate("yyyy-MM-dd")
//                        .dateType(com.baomidou.mybatisplus.generator.config.rules.DateType.TIME_PACK)
//                        .outputDir(System.getProperty("user.dir") + "/src/main/java")
//                        .disableOpenDir()
//                )
//                .packageConfig(builder -> builder
//                        .parent(BASE_PACKAGE)
//                        .entity("entity")
//                        .mapper("mapper")
//                        .service("service")
//                        .serviceImpl("service.impl")
//                        .controller("controller")
//                )
//                .strategyConfig(builder -> builder
//                        .addInclude(allTables) //所有表
//                        .addTablePrefix(TABLE_PREFIX) //表前缀过滤
//                        .entityBuilder()
//                        .enableLombok() //方便省略getter/setter
//                        .superClass((table) -> auditMap.getOrDefault(table.getName(), false) ? BASE_AUDIT_ENTITY : null)
//                        .superClass((table) -> auditMap.getOrDefault(table.getName(), false) ? BASE_AUDIT_ENTITY : null)
//                        .logicDeleteColumnName(LOGIC_DEL_FIELD)
//                        .logicDeletePropertyName(LOGIC_DEL_FIELD)
//                        .addTableFills(new Column("create_time", FieldFill.INSERT))
//                        .addTableFills(new Column("update_time", FieldFill.INSERT_UPDATE))
//                        .enableTableFieldAnnotation()
//                        .naming(NamingStrategy.underline_to_camel)
//                        .columnNaming(NamingStrategy.underline_to_camel)
//                        .build()
//                        .mapperBuilder()
//                        .formatMapperFileName("%sMapper")
//                        .enableMapperAnnotation()
//                        .build()
//                        .serviceBuilder()
//                        .formatServiceFileName("%sService")
//                        .formatServiceImplFileName("%sServiceImpl")
//                        .build()
//                        .controllerBuilder()
//                        .enableRestStyle()
//                        .formatFileName("%sController")
//                        .build()
//                )
//                .templateEngine(new FreemarkerTemplateEngine())
//                .templateConfig(builder -> builder //全部用MP默认模板
//                )
//                .execute();
//    }
//
//    /**
//     * 判断表是否拥有全部审计字段
//     */
//    public static boolean hasAllAuditFields(String tableName) throws Exception {
//        Set<String> hasFields = new HashSet<>();
//        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
//            DatabaseMetaData metaData = conn.getMetaData();
//            try (ResultSet rs = metaData.getColumns(null, null, tableName, null)) {
//                while (rs.next()) {
//                    hasFields.add(rs.getString("COLUMN_NAME").toLowerCase());
//                }
//            }
//        }
//        return hasFields.containsAll(AUDIT_FIELDS);
//    }
//}