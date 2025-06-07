package org.wabc.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;



/**
 * 角色与权限关联表DTO
 * @author wabc
 * @date 2025-05-18
 */
@Data
@Schema(description = "角色与权限关联表DTO")
public class SysRoleMenuDTO {

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