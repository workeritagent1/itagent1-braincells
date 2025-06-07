<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--
  ${table.comment!table.name} MyBatis-Plus XML映射文件
  数据表：${table.name}
  @author ${author}
  @date ${date}
-->
<mapper namespace="${mapperPackage}.${entity}Mapper">

    <resultMap id="BaseResultMap" type="${entityPackage}.${entity}">
<#list table.fields as field>
        <result property="${field.propertyName}" column="${field.name}" />
</#list>
    </resultMap>

    <sql id="Base_Column_List">
        ${table.fields?map(f -> f.name)?join(', ')}
    </sql>

</mapper>