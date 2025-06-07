package org.wabc.system.dto;

import org.wabc.commons.dto.PageQueryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * 角色与权限关联表分页查询DTO
 * @author wabc
 * @date 2025-05-18
 */
@Data
@Schema(description = "角色与权限关联表分页查询DTO")
public class SysRoleMenuPageDTO extends PageQueryDTO {

    /**
     * 关联的角色 ID
     */
    @Schema(description = "关联的角色 ID")
    private Long roleId;
    /**
     * 关联的权限 ID
     */
    @Schema(description = "关联的权限 ID")
    private Long menuId;
}