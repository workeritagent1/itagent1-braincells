<#-- 自动依赖判断 -->
<#assign useLocalDateTime = false>
<#assign useTableId = false>
<#assign useLogicDelete = false>
<#list table.fields as f>
    <#if f.propertyType?? && f.propertyType == "LocalDateTime">
        <#assign useLocalDateTime = true>
    </#if>
    <#if f.keyFlag?? && f.keyFlag>
        <#assign useTableId = true>
    </#if>
    <#if f.name?? && f.propertyType?? && f.name?lower_case == "deleted" && f.propertyType == "Byte">
        <#assign useLogicDelete = true>
    </#if>
</#list>

<#-- 判断是否继承BaseAuditEntity -->
<#assign auditFields = ["createdBy", "createdTime", "updatedBy", "updatedTime"]>
<#assign hasAudit = true>
<#list auditFields as af>
    <#assign hit = false>
    <#list table.fields as f>
        <#if f.propertyName?? && f.propertyName == af>
            <#assign hit = true>
            <#break>
        </#if>
    </#list>
    <#if !hit>
        <#assign hasAudit = false>
        <#break>
    </#if>
</#list>

package ${entityPackage};

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
<#if useLocalDateTime>
import java.time.LocalDateTime;
</#if>
<#if useTableId>
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
</#if>
<#if useLogicDelete>
import com.baomidou.mybatisplus.annotation.TableLogic;
</#if>
<#if hasAudit>
import org.wabc.commons.entity.BaseAuditEntity;
</#if>

/**
 * ${table.comment!table.name}实体类
 * @author ${author}
 * @date ${date}
 */
@Data
@Schema(description = "${table.comment!table.name}实体")
public class ${entity}<#if hasAudit> extends BaseAuditEntity</#if> {

<#list table.fields as field>
    <#if hasAudit && (auditFields?seq_contains(field.propertyName))>
    <#-- 跳过已被基类继承的审计字段 -->
    <#else>
    /**
     * ${field.comment!field.name}
     */
    @Schema(description = "${field.comment!field.name}")
    <#if field.keyFlag?? && field.keyFlag>
    @TableId(type = IdType.AUTO)
    </#if>
    <#if field.name?? && field.propertyType?? && field.name?lower_case == "deleted" && field.propertyType == "Byte">
    @TableLogic
    private Integer ${field.propertyName};
    <#elseif field.propertyType?? && field.propertyType == "LocalDateTime">
    private LocalDateTime ${field.propertyName};
    <#elseif field.propertyType?? && field.propertyType == "String">
    private String ${field.propertyName};
    <#elseif field.propertyType?? && field.propertyType == "Long">
    private Long ${field.propertyName};
    <#elseif field.propertyType?? && field.propertyType == "Byte">
    private Integer ${field.propertyName};
    <#elseif field.propertyType?? && field.propertyType == "Integer">
    private Integer ${field.propertyName};
    <#else>
    private ${field.propertyType!""} ${field.propertyName};
    </#if>
    </#if>
</#list>
}