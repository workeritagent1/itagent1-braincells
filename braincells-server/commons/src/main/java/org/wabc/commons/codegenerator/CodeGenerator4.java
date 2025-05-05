package org.wabc.commons.codegenerator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.Entity;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import org.wabc.commons.model.entity.BaseAuditEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * 智能代码生成器（支持动态审计字段处理）
 */
public class CodeGenerator4 {

    // 数据库配置
    private static final String JDBC_URL = "jdbc:mysql://192.168.182.131:3306/2025wabc?useSSL=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "dev123";

    // 项目路径
    private static final String PROJECT_PATH = System.getProperty("user.dir");
    // 包配置
    private static final String BASE_PACKAGE = "org.wabc";
    private static final String MODULE_NAME = "system";

    // 审计字段定义
    private static final Set<String> AUDIT_COLUMNS = new HashSet<>(Arrays.asList(
            "created_by", "created_time", "updated_by", "updated_time"
    ));
    // 逻辑删除字段名
    private static final String LOGIC_DEL_FIELD = "deleted";


    /**
     * 需要生成的目标表
     */
    private static List<String> getTargetTables() {
        return Arrays.asList(
                "sys_dept", "sys_user", "sys_role",
                "sys_user_role", "sys_menu", "sys_role_menu",
                "sys_oauth2_client", "sys_oauth2_authorization"
        );
    }

    public static void main(String[] args) throws Exception {
//        List<String> agroup= Arrays.asList(
//                "sys_dept", "sys_user", "sys_role",
//                "sys_user_role", "sys_menu", "sys_role_menu",
//                "sys_oauth2_client", "sys_oauth2_authorization"
//        );
//        List<String> bgroup= Arrays.asList("sys_user_role", "sys_role_menu");
//
//        codeGenerate(agroup,"sys_",true,true,"commons");
//        codeGenerate(bgroup,"sys_",false,false,"commons");


        List<String> tableNames = getTargetTables();
        for (String tableName : tableNames) {
            codeGenerate(tableName,"sys_","system");
        }


    }

    /**
     * 代码生成器通用方法
     * @param tableNames 表名称集合
     * @param tablePreffix 表名称前缀 sys_
     * @param hasAuditColumns 是否有审计字段
     * @param hasDeleteColumn 是否有逻辑删除字段
     * @param moduleName 模块名称sysadmin,oauth2,用于存放：org.wabc.*下生成代码；在*../src/main/resources/mapper/下生成mapper.
     */
    public static void codeGenerate(List<String> tableNames,String tablePreffix,boolean hasAuditColumns,boolean hasDeleteColumn, String moduleName){

        FastAutoGenerator.create(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)
                .globalConfig(builder -> {
                    builder.author("wabc")
                            .enableSwagger()
                            .disableOpenDir()
                            .outputDir(PROJECT_PATH + "/commons/src/main/java")
                            .commentDate("yyyy-MM-dd");
                })
                .packageConfig(builder -> {
                    builder.parent(BASE_PACKAGE)
                            .moduleName(moduleName)
                            .entity("model.entity")
                            .mapper("mapper")
                            .service("service")
                            .serviceImpl("service.impl")
                            .controller("controller")
                            .pathInfo(Collections.singletonMap(OutputFile.xml,
                                    PROJECT_PATH + "/" + moduleName +"/src/main/resources/mapper/"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tableNames)
                            .addTablePrefix(tablePreffix) // "sys_"
                            // 实体策略
                            .entityBuilder()
                            .enableLombok();
                    if(hasAuditColumns){
                        builder.entityBuilder()
                            .superClass(BaseAuditEntity.class);
                    }
                    builder.entityBuilder()
                            .naming(NamingStrategy.underline_to_camel)
                            .columnNaming(NamingStrategy.underline_to_camel)
                            .enableChainModel();
                    if(hasDeleteColumn){
                        builder.entityBuilder()
                             .logicDeleteColumnName(LOGIC_DEL_FIELD)
                             .logicDeletePropertyName(LOGIC_DEL_FIELD);
                    }

                    builder.entityBuilder()
                            // 「插入,修改」数据时，MyBatis-Plus会自动为 created_time 字段赋值
                            .addTableFills(new Column("created_time", FieldFill.INSERT))
                            .addTableFills(new Column("updated_time", FieldFill.INSERT_UPDATE))
                            .enableFileOverride()

                            // Mapper策略
                            .mapperBuilder()
                            .enableMapperAnnotation()
                            .enableBaseResultMap()
                            .enableBaseColumnList()

                            // Controller策略
                            .controllerBuilder()
                            .enableRestStyle()
                            .enableHyphenStyle();
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();

    }



    /**
     * 判断表是否包含所有审计字段
     */
    public static boolean hasAllAuditColumns(String tableName) {
        Set<String> columns = getTableColumns(tableName);
        return columns.containsAll(AUDIT_COLUMNS);
    }

    /**
     * 判断表是否包含逻辑删除标志字段
     */
    public static boolean hasLogicDelete(String tableName) {
        Set<String> columns = getTableColumns(tableName);
        return columns.contains(LOGIC_DEL_FIELD);
    }

    /**
     * 获取某表所有字段名
     */
    public static Set<String> getTableColumns(String tableName) {
        Set<String> columns = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             ResultSet rs = conn.getMetaData().getColumns(null, null, tableName, null)) {
            while (rs.next()) {
                columns.add(rs.getString("COLUMN_NAME").toLowerCase());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columns;
    }



    /**
     * 对单张表生成代码，支持自动处理审计/逻辑删除策略
     * @param tableName
     * @param tablePrefix 自动过滤 sys_ 前缀
     * @param moduleName 模块名称commons
     */
    public static void codeGenerate(String tableName,String tablePrefix,String moduleName) {
        boolean hasAudit = hasAllAuditColumns(tableName);
        boolean hasLogicDel = hasLogicDelete(tableName);

        //

        // Output 目录结构: entity等输出到commons，mapper xml 输出到sysadmin模块
        FastAutoGenerator.create(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)
                .globalConfig(builder -> builder
                        .author("wabc")
                        .outputDir(PROJECT_PATH + "/"+moduleName+"/src/main/java")
                        .disableOpenDir()
                        .commentDate("yyyy-MM-dd")
                        .enableSwagger() // 实际生成时推荐为true，因为我们需要@Schema，后续统一替换
                )
                .packageConfig(builder -> builder
                        .parent(BASE_PACKAGE)
                        .moduleName(moduleName)
                        .entity("model.entity")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .controller("controller")
                        .pathInfo(Collections.singletonMap(
                                OutputFile.xml,
                                PROJECT_PATH + "/" + moduleName + "/src/main/resources/mapper/"
                        ))
                )
                .strategyConfig(builder -> {
                    builder.addInclude(tableName)
                            .addTablePrefix(tablePrefix);
                    // ---------- Entity 策略 ----------
                    Entity.Builder entityBuilder = builder.entityBuilder()
                            .enableLombok()
                            .naming(NamingStrategy.underline_to_camel)
                            .columnNaming(NamingStrategy.underline_to_camel)
                            .enableChainModel()
                            .enableFileOverride()
                            .addTableFills(new Column("created_time", FieldFill.INSERT))
                            .addTableFills(new Column("updated_time", FieldFill.INSERT_UPDATE));
                    // 按需增加基类和/或逻辑删除
                    if (hasAudit) {
                        entityBuilder.superClass(BaseAuditEntity.class);
                    }
                    if (hasLogicDel) {
                        entityBuilder
                                .logicDeleteColumnName(LOGIC_DEL_FIELD)
                                .logicDeletePropertyName(LOGIC_DEL_FIELD);
                    }
                    // ---------- Mapper ----------
                    builder.mapperBuilder()
                            .enableMapperAnnotation()
                            .enableBaseResultMap()
                            .enableBaseColumnList();
                    // ---------- Service ----------
                    builder.serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl");
                    // ---------- Controller ----------
                    builder.controllerBuilder()
                            .enableRestStyle()
                            .formatFileName("%sController")
                            .enableHyphenStyle();
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }



}
