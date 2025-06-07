

package org.wabc.system.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 角色与权限关联表实体类
 * @author wabc
 * @date 2025-05-18
 */
@Data
@Schema(description = "角色与权限关联表实体")
public class SysRoleMenu {

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