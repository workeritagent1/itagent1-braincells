<#assign useLocalDateTime = false>
<#list table.fields as f>
    <#if f.propertyType?? && f.propertyType == "LocalDateTime">
        <#assign useLocalDateTime = true>
        <#break>
    </#if>
</#list>
package ${dtoPackage};

import org.wabc.commons.dto.PageQueryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
<#if useLocalDateTime>import java.time.LocalDateTime;</#if>

/**
 * ${table.comment!table.name}分页查询DTO
 * @author ${author}
 * @date ${date}
 */
@Data
@Schema(description = "${table.comment!table.name}分页查询DTO")
public class ${entity}PageDTO extends PageQueryDTO {

<#list table.fields as field>
    /**
     * ${field.comment!field.name}
     */
    @Schema(description = "${field.comment!field.name}")
    <#if field.propertyType?? && field.propertyType == "LocalDateTime">
    private LocalDateTime ${field.propertyName};
    <#elseif field.propertyType?? && field.propertyType == "Long">
    private Long ${field.propertyName};
    <#elseif field.propertyType?? && field.propertyType == "Byte">
    private Integer ${field.propertyName};
    <#elseif field.propertyType?? && field.propertyType == "Integer">
    private Integer ${field.propertyName};
    <#elseif field.propertyType?? && field.propertyType == "String">
    private String ${field.propertyName};
    <#else>
    private ${field.propertyType} ${field.propertyName};
    </#if>
</#list>
}