<#-- 只校验字符串：有长度用字段长度，无长度但为text时max=65535，不为String类型则不校验 -->
<#assign useLocalDateTime = false>
<#assign useSize = false>
<#list table.fields as f>
    <#if f.propertyType?? && f.propertyType == "LocalDateTime">
        <#assign useLocalDateTime = true>
    </#if>
    <#if f.propertyType?? && f.propertyType == "String">
        <#assign useSize = true>
    </#if>
</#list>
package ${dtoPackage};

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
<#if useSize>import javax.validation.constraints.Size;</#if>
<#if useLocalDateTime>import java.time.LocalDateTime;</#if>

/**
 * ${table.comment!table.name}DTO
 * @author ${author}
 * @date ${date}
 */
@Data
@Schema(description = "${table.comment!table.name}DTO")
public class ${entity}DTO {

<#list table.fields as field>
    /**
     * ${field.comment!field.name}
     */
    @Schema(description = "${field.comment!field.name}")
    <#if field.propertyType?? && field.propertyType == "String" && field.length??>
    @Size(min = 1, max = ${field.length}, message = "${field.comment!field.name}长度为1-${field.length}")
    private String ${field.propertyName};
    <#elseif field.propertyType?? && field.propertyType == "String" && field.type?? && field.type?starts_with("text")>
    @Size(min = 1, max = 65535, message = "${field.comment!field.name}长度为1-65535")
    private String ${field.propertyName};
    <#elseif field.propertyType?? && field.propertyType == "String">
    @Size(min = 1, max = 255, message = "${field.comment!field.name}长度为1-255")
    private String ${field.propertyName};
    <#elseif field.propertyType?? && field.propertyType == "LocalDateTime">
    private LocalDateTime ${field.propertyName};
    <#elseif field.propertyType?? && field.propertyType == "Long">
    private Long ${field.propertyName};
    <#elseif field.propertyType?? && field.propertyType == "Byte">
    private Integer ${field.propertyName};
    <#elseif field.propertyType?? && field.propertyType == "Integer">
    private Integer ${field.propertyName};
    <#else>
    private ${field.propertyType!""} ${field.propertyName};
    </#if>

</#list>
}